package inf112.skeleton.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import inf112.skeleton.model.entities.FastEnemy;
import inf112.skeleton.model.entities.Player;
import inf112.skeleton.model.factories.RoomFactory;
import inf112.skeleton.model.generation.RoomTemplate;
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
    private PlayerInputHandler playerInputHandler;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        gameView = new GameView();
        gameView.create(1280, 768);

        Room room = createExampleRoom();
        gameWorld = new GameWorld(room);

        player = new Player(200, 200);
        gameWorld.addEntity(player);

        fastEnemy = new FastEnemy(300, 300, 32, 32, 48, 2, player);
        gameWorld.addEntity(fastEnemy);

        playerInputHandler = new PlayerInputHandler(player);
        Gdx.input.setInputProcessor(playerInputHandler);

        gameView.updateCamera(player.getX(), player.getY());
    }

    private Room createExampleRoom() {
        String[] pattern = {
                "####################",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "####################"
        };
        RoomFactory factory = new RoomTemplate(pattern);
        return factory.createRoom();
    }

    @Override
    public void render(float delta) {
        gameWorld.step(delta);

        if (player.getHealth() <= 0) {
            game.setScreen(new TitleScreen(game));
        }

        gameView.updateCamera(player.getX(), player.getY());

        gameView.render(gameWorld.getRoom(), gameWorld.getEntities());

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