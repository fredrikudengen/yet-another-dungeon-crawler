package inf112.dungeoncrawler.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.sound.SoundService;
import inf112.dungeoncrawler.view.GameView;
import inf112.dungeoncrawler.view.screens.PauseOverlay;
import java.lang.reflect.Field;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameScreenTest {

	private static final SoundService NO_SOUND = effect -> {};

	private Input originalInput;
	private Input input;
	private GameView gameView;
	private GameWorld gameWorld;
	private PauseOverlay pauseOverlay;
	private Player player;
	private GameScreen gameScreen;

	@BeforeEach
	void setUp() {
		originalInput = Gdx.input;
		input = mock(Input.class);
		Gdx.input = input;

		Game game = mock(Game.class);
		gameView = mock(GameView.class);
		gameWorld = mock(GameWorld.class);
		pauseOverlay = mock(PauseOverlay.class);
		player = new Player(128, 256, NO_SOUND);

		gameScreen = new GameScreen(game);
		setPrivateField(gameScreen, "gameView", gameView);
		setPrivateField(gameScreen, "gameWorld", gameWorld);
		setPrivateField(gameScreen, "pauseOverlay", pauseOverlay);
		setPrivateField(gameScreen, "player", player);
	}

	@AfterEach
	void tearDown() {
		Gdx.input = originalInput;
	}

	@Test
	void renderWhenNotPausedStepsWorldAndRendersView() {
		when(input.isKeyJustPressed(Input.Keys.ESCAPE)).thenReturn(false);

		gameScreen.render(0.25f);

		verify(gameWorld).step(0.25f);
		verify(gameView).updateCamera(128f, 256f);
		verify(gameView).render(0.25f, gameWorld);
		verify(pauseOverlay, never()).render();
	}

	@Test
	void renderWhenPausedRendersOverlayOnly() {
		when(input.isKeyJustPressed(Input.Keys.ESCAPE)).thenReturn(false);
		setPrivateField(gameScreen, "isPaused", true);

		gameScreen.render(0.5f);

		verify(gameWorld, never()).step(anyFloat());
		verify(gameView, never()).updateCamera(anyFloat(), anyFloat());
		verify(gameView, never()).render(anyFloat(), eq(gameWorld));
		verify(pauseOverlay).render();
	}

	@Test
	void renderEscapeKeyTogglesPauseAndRendersOverlay() {
		when(input.isKeyJustPressed(Input.Keys.ESCAPE)).thenReturn(true);

		gameScreen.render(0.016f);

		verify(gameWorld, never()).step(anyFloat());
		verify(gameView, never()).render(anyFloat(), eq(gameWorld));
		verify(pauseOverlay).render();
	}

	@Test
	void resizeDelegatesToGameView() {
		gameScreen.resize(1024, 768);

		verify(gameView).resize(1024, 768);
	}

	@Test
	void hideClearsInputProcessor() {
		gameScreen.hide();

		verify(input).setInputProcessor(null);
	}

	@Test
	void pauseAndResumeDoNotThrow() {
		assertDoesNotThrow(() -> gameScreen.pause());
		assertDoesNotThrow(() -> gameScreen.resume());
	}

	private static void setPrivateField(Object target, String fieldName, Object value) {
		try {
			Field field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(target, value);
		} catch (ReflectiveOperationException e) {
			throw new AssertionError("Failed to set field: " + fieldName, e);
		}
	}
}
