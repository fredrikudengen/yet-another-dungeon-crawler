package inf112.skeleton.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import inf112.skeleton.model.entities.enemies.FastEnemy;
import inf112.skeleton.model.entities.enemies.NormalEnemy;
import inf112.skeleton.model.entities.enemies.SlowEnemy;
import inf112.skeleton.model.entities.powerups.AttackPowerup;
import inf112.skeleton.model.entities.powerups.HealthPowerup;
import inf112.skeleton.model.entities.Player;
import inf112.skeleton.model.entities.powerups.SpeedPowerup;
import inf112.skeleton.model.factories.RoomFactory;
import inf112.skeleton.model.generation.RoomLoader;
import inf112.skeleton.model.generation.SpawnPoint;
import inf112.skeleton.model.world.GameWorld;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.view.GameView;
import inf112.skeleton.view.Menu.PauseScreen;
import inf112.skeleton.view.Menu.TitleScreen;

public class GameScreen implements Screen {
    private Game game;
    private GameView gameView;
    private GameWorld gameWorld;
    private Player player;
    private FastEnemy fastEnemy;
    private HealthPowerup healthPowerup;
    private SpeedPowerup speedPowerup;
    private PlayerInputHandler playerInputHandler;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        Room room = createExampleRoom();
        gameWorld = new GameWorld(room);

        gameView = new GameView();
        gameView.create(1280, 768);

        for (SpawnPoint sp : room.getSpawnPoints()) {
            if (sp.type == SpawnPoint.Type.PLAYER) {
                player = new Player(sp.x, sp.y);
                gameWorld.addEntity(player);
                break;
            }
        }
        for (SpawnPoint sp : room.getSpawnPoints()) {
            if (sp.type == SpawnPoint.Type.FASTENEMY) {
                gameWorld.addEntity(new FastEnemy(sp.x, sp.y, player));
            }
            else if (sp.type == SpawnPoint.Type.NORMALENEMY) {
                gameWorld.addEntity(new NormalEnemy(sp.x, sp.y, player));
            }
            else if (sp.type == SpawnPoint.Type.SLOWENEMY) {
                gameWorld.addEntity(new SlowEnemy(sp.x, sp.y, player));
            }
            else if (sp.type == SpawnPoint.Type.SPEEDPOWERUP) {
                gameWorld.addPowerup(new SpeedPowerup(sp.x, sp.y));
            }
            else if (sp.type == SpawnPoint.Type.ATTACKPOWERUP) {
                gameWorld.addPowerup(new AttackPowerup(sp.x, sp.y));
            }
            else if (sp.type == SpawnPoint.Type.HEALTHPOWERUP) {
                gameWorld.addPowerup(new HealthPowerup(sp.x, sp.y));
            }
        }

        playerInputHandler = new PlayerInputHandler(player);
        Gdx.input.setInputProcessor(playerInputHandler);
        gameView.updateCamera(player.getX(), player.getY());
    }

    private Room createExampleRoom() {
        RoomFactory factory = new RoomLoader("rooms/room3.txt");
        return factory.createRoom();
    }

    @Override
    public void render(float delta) {
        gameWorld.step(delta, player);

        if (player.getHealth() <= 0) {
            game.setScreen(new TitleScreen(game));
        }

        gameView.updateCamera(player.getX(), player.getY());

        gameView.render(delta, gameWorld.getRoom(), gameWorld.getEntities(), gameWorld.getPowerups());

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game, this));
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
    }
}