package inf112.dungeoncrawler.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import inf112.dungeoncrawler.controller.PlayerInputHandler;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.sound.SoundService;
import inf112.dungeoncrawler.view.GameView;

import java.util.Objects;

public class GameScreen implements Screen {

    private final Game game;
    private final GameWorld gameWorld;
    private final SoundService sound;

    private GameView gameView;
    private PauseOverlay pauseOverlay;
    private boolean isPaused = false;

    public GameScreen(Game game, GameWorld gameWorld, SoundService sound) {
        this.game      = Objects.requireNonNull(game,      "game must not be null");
        this.gameWorld = Objects.requireNonNull(gameWorld, "gameWorld must not be null");
        this.sound     = Objects.requireNonNull(sound,     "sound must not be null");
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
        Player player = gameWorld.getPlayer();
        gameView.updateCamera(player.getX(), player.getY());
    }

    private void draw(float delta) {
        gameView.render(delta, gameWorld);
        if (isPaused) {
            pauseOverlay.render();
        }
    }

    private void checkGameOver() {
        if (gameWorld.getPlayer().getHealth() <= 0) {
            game.setScreen(new GameOverScreen(game, sound, gameWorld.getKilledEnemies()));
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