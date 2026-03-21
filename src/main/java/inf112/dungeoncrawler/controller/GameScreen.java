package inf112.dungeoncrawler.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import inf112.dungeoncrawler.model.entities.enemies.FastEnemy;
import inf112.dungeoncrawler.model.entities.enemies.NormalEnemy;
import inf112.dungeoncrawler.model.entities.enemies.SlowEnemy;
import inf112.dungeoncrawler.model.entities.powerups.AttackPowerup;
import inf112.dungeoncrawler.model.entities.powerups.HealthPowerup;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.powerups.SpeedPowerup;
import inf112.dungeoncrawler.model.factories.EntityFactory;
import inf112.dungeoncrawler.model.factories.RoomFactory;
import inf112.dungeoncrawler.model.generation.RoomLoader;
import inf112.dungeoncrawler.model.generation.SpawnPoint;
import inf112.dungeoncrawler.sound.SoundPlayer;
import inf112.dungeoncrawler.sound.SoundService;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.view.GameView;
import inf112.dungeoncrawler.view.screens.GameOverScreen;
import inf112.dungeoncrawler.view.screens.PauseOverlay;

public class GameScreen implements Screen {
    private Game game;
    private GameView gameView;
    private GameWorld gameWorld;
    private Player player;
    private PlayerInputHandler playerInputHandler;
    private PauseOverlay pauseOverlay;
    private boolean isPaused = false;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        Room room = createExampleRoom();
        SoundService sound = SoundPlayer.getInstance();

        gameView = new GameView();
        gameView.create(1280, 768);

        sound.playMusic();

        for (SpawnPoint sp : room.getSpawnPoints()) {
            if (sp.type == SpawnPoint.Type.PLAYER) {
                player = new Player(sp.x, sp.y, sound);
                break;
            }
        }

        gameWorld = new GameWorld(room, player);
        gameWorld.addEntity(player);

        EntityFactory factory = new EntityFactory();
        factory.register(SpawnPoint.Type.FASTENEMY,    (x, y) -> new FastEnemy(x, y, player));
        factory.register(SpawnPoint.Type.NORMALENEMY,  (x, y) -> new NormalEnemy(x, y, player));
        factory.register(SpawnPoint.Type.SLOWENEMY,    (x, y) -> new SlowEnemy(x, y, player));
        factory.register(SpawnPoint.Type.HEALTHPOWERUP,(x, y) -> new HealthPowerup(x, y, sound));
        factory.register(SpawnPoint.Type.SPEEDPOWERUP, (x, y) -> new SpeedPowerup(x, y, sound));
        factory.register(SpawnPoint.Type.ATTACKPOWERUP,(x, y) -> new AttackPowerup(x, y, sound));

        for (SpawnPoint sp : room.getSpawnPoints()) {
            if (sp.type != SpawnPoint.Type.PLAYER) {
                gameWorld.addEntity(factory.spawn(sp));
            }
        }

        playerInputHandler = new PlayerInputHandler(player);
        Gdx.input.setInputProcessor(playerInputHandler);
        gameView.updateCamera(player.getX(), player.getY());

        pauseOverlay = new PauseOverlay(
                game,
                () -> isPaused = false,
                () -> this.dispose()
        );
        pauseOverlay.init();
    }

    private Room createExampleRoom() {
        RoomFactory factory = new RoomLoader("rooms/room3.txt");
        return factory.createRoom();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
        }

        if (!isPaused) {
            gameWorld.step(delta);
            gameView.updateCamera(player.getX(), player.getY());
            gameView.render(delta, gameWorld);        }

        if (player.getHealth() <= 0) {
            game.setScreen(new GameOverScreen(game, gameWorld));
            return;
        }

        if (isPaused) {
            pauseOverlay.render();
        }
    }

    @Override
    public void resize(int width, int height) {
        gameView.resize(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        gameView.dispose();
        pauseOverlay.dispose();
        SoundPlayer.getInstance().dispose();
    }
}