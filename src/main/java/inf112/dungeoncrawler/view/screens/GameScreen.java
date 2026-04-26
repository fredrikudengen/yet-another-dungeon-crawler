package inf112.dungeoncrawler.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import inf112.dungeoncrawler.controller.PlayerInputHandler;
import inf112.dungeoncrawler.model.factories.GameWorldBuilder;
import inf112.dungeoncrawler.model.generation.RoomLoader;
import inf112.dungeoncrawler.model.generation.SpawnPoint;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.model.world.TileType;
import inf112.dungeoncrawler.sound.SoundService;
import inf112.dungeoncrawler.view.GameView;

import java.util.Objects;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public class GameScreen implements Screen {

    private static final String ROOM_1_PATH = "rooms/room1.txt";
    private static final String ROOM_2_PATH = "rooms/room2.txt";
    private static final String ROOM_3_PATH = "rooms/room3.txt";
    private static final float DOOR_TRANSITION_COOLDOWN_SECONDS = 0.2f;

    private enum DoorSide {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    private static class TransitionSpec {
        private final String targetRoomPath;
        private final DoorSide targetEntrySide;

        private TransitionSpec(String targetRoomPath, DoorSide targetEntrySide) {
            this.targetRoomPath = targetRoomPath;
            this.targetEntrySide = targetEntrySide;
        }
    }

    private final Game game;
    private final SoundService sound;
    private GameWorld gameWorld;
    private String currentRoomPath;

    private GameView gameView;
    private PauseOverlay pauseOverlay;
    private boolean isPaused = false;
    private float doorTransitionCooldown = 0f;
    private int totalEnemiesKilled = 0;

    public GameScreen(Game game, GameWorld gameWorld, SoundService sound) {
        this(game, gameWorld, sound, ROOM_3_PATH);
    }

    public GameScreen(Game game, GameWorld gameWorld, SoundService sound, String currentRoomPath) {
        this.game      = Objects.requireNonNull(game,      "game must not be null");
        this.gameWorld = Objects.requireNonNull(gameWorld, "gameWorld must not be null");
        this.sound     = Objects.requireNonNull(sound,     "sound must not be null");
        this.currentRoomPath = Objects.requireNonNull(currentRoomPath, "currentRoomPath must not be null");
    }

    @Override
    public void show() {
        gameView = new GameView();
        gameView.create(1280, 768);
        sound.playMusic();

        Player player = gameWorld.getPlayer();
        gameView.updateCamera(player.getX(), player.getY());

        PlayerInputHandler inputHandler = new PlayerInputHandler(player);
        Gdx.input.setInputProcessor(inputHandler);

        pauseOverlay = new PauseOverlay(game, sound, () -> isPaused = false);
        pauseOverlay.init();
    }

    @Override
    public void render(float delta) {
        handlePauseInput();

        if (!isPaused) {
            update(delta);
        }

        draw(delta);
        checkGameOver();
    }

    private void handlePauseInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
        }
    }

    private void update(float delta) {
        gameWorld.step(delta);

        if (!gameWorld.hasEnemiesRemaining()) {
            gameWorld.openDoors();
        }

        if (doorTransitionCooldown > 0f) {
            doorTransitionCooldown = Math.max(0f, doorTransitionCooldown - delta);
        }

        handleDoorTransition();

        Player player = gameWorld.getPlayer();
        gameView.updateCamera(player.getX(), player.getY());
    }

    private void handleDoorTransition() {
        if (doorTransitionCooldown > 0f) {
            return;
        }

        Player player = gameWorld.getPlayer();
        Room room = gameWorld.getRoom();

        DoorSide currentDoorSide = findTouchedOpenDoorSide(room, player);
        if (currentDoorSide == null) {
            return;
        }

        TransitionSpec transition = getTransition(currentRoomPath, currentDoorSide);
        if (transition == null) {
            return;
        }

        transitionToRoom(transition);
        doorTransitionCooldown = DOOR_TRANSITION_COOLDOWN_SECONDS;
    }

    private DoorSide findTouchedOpenDoorSide(Room room, Player player) {
        float tolerance = TILE_SIZE * 0.2f;

        for (SpawnPoint spawnPoint : room.getSpawnPoints()) {
            if (spawnPoint.type != SpawnPoint.Type.DOOR) {
                continue;
            }

            int tileX = spawnPoint.x / TILE_SIZE;
            int tileY = spawnPoint.y / TILE_SIZE;

            if (room.getTileAt(tileX, tileY) != TileType.DOOR_OPEN) {
                continue;
            }

            DoorSide side = getDoorSide(room, tileX, tileY);
            if (side == null) {
                continue;
            }

            float doorX = tileX * TILE_SIZE;
            float doorY = tileY * TILE_SIZE;
            if (rectanglesIntersect(
                    player.getX(), player.getY(), player.getWidth(), player.getHeight(),
                    doorX - tolerance, doorY - tolerance,
                    TILE_SIZE + tolerance * 2f, TILE_SIZE + tolerance * 2f)) {
                return side;
            }
        }

        return null;
    }

    private boolean rectanglesIntersect(float ax, float ay, float aw, float ah,
                                        float bx, float by, float bw, float bh) {
        return ax < bx + bw
                && ax + aw > bx
                && ay < by + bh
                && ay + ah > by;
    }

    private void transitionToRoom(TransitionSpec transition) {
        totalEnemiesKilled += gameWorld.getKilledEnemies();

        Room targetRoom = new RoomLoader(transition.targetRoomPath).createRoom();
        float[] entryPosition = findEntryPosition(targetRoom, transition.targetEntrySide);

        Player player = gameWorld.getPlayer();
        gameWorld = new GameWorldBuilder(targetRoom, sound).buildWithPlayer(player, entryPosition[0], entryPosition[1]);
        currentRoomPath = transition.targetRoomPath;
    }

    private float[] findEntryPosition(Room room, DoorSide entryDoorSide) {
        SpawnPoint doorSpawnPoint = findDoorSpawnPoint(room, entryDoorSide);
        if (doorSpawnPoint == null) {
            for (SpawnPoint spawnPoint : room.getSpawnPoints()) {
                if (spawnPoint.type == SpawnPoint.Type.PLAYER) {
                    return new float[]{spawnPoint.x, spawnPoint.y};
                }
            }
            return new float[]{TILE_SIZE, TILE_SIZE};
        }

        int doorTileX = doorSpawnPoint.x / TILE_SIZE;
        int doorTileY = doorSpawnPoint.y / TILE_SIZE;

        int spawnTileX = doorTileX;
        int spawnTileY = doorTileY;

        switch (entryDoorSide) {
            case LEFT -> spawnTileX = doorTileX + 1;
            case RIGHT -> spawnTileX = doorTileX - 1;
            case TOP -> spawnTileY = doorTileY - 1;
            case BOTTOM -> spawnTileY = doorTileY + 1;
        }

        spawnTileX = Math.max(0, Math.min(room.getWidth() - 1, spawnTileX));
        spawnTileY = Math.max(0, Math.min(room.getHeight() - 1, spawnTileY));
        return new float[]{spawnTileX * TILE_SIZE, spawnTileY * TILE_SIZE};
    }

    private SpawnPoint findDoorSpawnPoint(Room room, DoorSide side) {
        for (SpawnPoint spawnPoint : room.getSpawnPoints()) {
            if (spawnPoint.type != SpawnPoint.Type.DOOR) {
                continue;
            }

            int tileX = spawnPoint.x / TILE_SIZE;
            int tileY = spawnPoint.y / TILE_SIZE;

            if (isDoorOnSide(room, side, tileX, tileY)) {
                return spawnPoint;
            }
        }
        return null;
    }

    private boolean isDoorOnSide(Room room, DoorSide side, int tileX, int tileY) {
        return switch (side) {
            case LEFT -> tileX == 0;
            case RIGHT -> tileX == room.getWidth() - 1;
            case TOP -> tileY == room.getHeight() - 1;
            case BOTTOM -> tileY == 0;
        };
    }

    private DoorSide getDoorSide(Room room, int tileX, int tileY) {
        if (tileX == 0) {
            return DoorSide.LEFT;
        }
        if (tileX == room.getWidth() - 1) {
            return DoorSide.RIGHT;
        }
        if (tileY == room.getHeight() - 1) {
            return DoorSide.TOP;
        }
        if (tileY == 0) {
            return DoorSide.BOTTOM;
        }
        return null;
    }

    private TransitionSpec getTransition(String roomPath, DoorSide exitDoorSide) {
        if (ROOM_3_PATH.equals(roomPath) && exitDoorSide == DoorSide.LEFT) {
            return new TransitionSpec(ROOM_2_PATH, DoorSide.RIGHT);
        }
        if (ROOM_2_PATH.equals(roomPath) && exitDoorSide == DoorSide.RIGHT) {
            return new TransitionSpec(ROOM_3_PATH, DoorSide.LEFT);
        }
        if (ROOM_2_PATH.equals(roomPath) && exitDoorSide == DoorSide.TOP) {
            return new TransitionSpec(ROOM_1_PATH, DoorSide.BOTTOM);
        }
        if (ROOM_1_PATH.equals(roomPath) && exitDoorSide == DoorSide.BOTTOM) {
            return new TransitionSpec(ROOM_2_PATH, DoorSide.TOP);
        }
        if (ROOM_1_PATH.equals(roomPath) && exitDoorSide == DoorSide.TOP) {
            return new TransitionSpec(ROOM_3_PATH, DoorSide.BOTTOM);
        }
        if (ROOM_3_PATH.equals(roomPath) && exitDoorSide == DoorSide.BOTTOM) {
            return new TransitionSpec(ROOM_1_PATH, DoorSide.TOP);
        }
        return null;
    }

    private void draw(float delta) {
        gameView.render(delta, gameWorld);
        if (isPaused) {
            pauseOverlay.render();
        }
    }

    private void checkGameOver() {
        if (gameWorld.getPlayer().getHealth() <= 0) {
            int totalKilledEnemies = totalEnemiesKilled + gameWorld.getKilledEnemies();
            game.setScreen(new GameOverScreen(game, sound, totalKilledEnemies));
        }
    }

    @Override
    public void resize(int width, int height) {
        gameView.resize(width, height);
    }

    @Override
    public void pause() { /* managed by render-loop via ESCAPE */ }

    @Override
    public void resume() { /* managed by render-loop via ESCAPE */ }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        gameView.dispose();
        pauseOverlay.dispose();
    }

    public boolean isPaused() {
        return isPaused;
    }
}