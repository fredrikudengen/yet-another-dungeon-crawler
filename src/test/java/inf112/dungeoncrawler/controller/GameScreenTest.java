package inf112.dungeoncrawler.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.sound.SoundService;
import inf112.dungeoncrawler.view.GameView;
import inf112.dungeoncrawler.view.screens.PauseOverlay;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameScreenTest {

	private static final SoundService NO_SOUND = effect -> {};

	private RecordingInput recordingInput;
	private RecordingGameView gameView;
	private RecordingGameWorld gameWorld;
	private RecordingPauseOverlay pauseOverlay;
	private Player player;
	private GameScreen gameScreen;

	@BeforeEach
	void setUp() {
		
		
		recordingInput = new RecordingInput();
		Gdx.input = createInputProxy(recordingInput);
		Gdx.graphics = createGraphicsProxy();

		Game game = new DummyGame();
		gameView = new RecordingGameView();
		player = new Player(128, 256, NO_SOUND);
		gameWorld = new RecordingGameWorld(player);
		pauseOverlay = new RecordingPauseOverlay(game);

		gameScreen = new GameScreen(game);
		setPrivateField(gameScreen, "gameView", gameView);
		setPrivateField(gameScreen, "gameWorld", gameWorld);
		setPrivateField(gameScreen, "pauseOverlay", pauseOverlay);
		setPrivateField(gameScreen, "player", player);
	}

	@Test
	void normalRenderWhenNotPaused() {

		gameScreen.render(0.25f);

		assertEquals(1, gameWorld.stepCallCount);
		assertEquals(0.25f, gameWorld.lastDelta);
		assertEquals(1, gameView.updateCameraCallCount);
		assertEquals(128f, gameView.lastCameraX);
		assertEquals(256f, gameView.lastCameraY);
		assertEquals(1, gameView.renderCallCount);
		assertEquals(0.25f, gameView.lastRenderDelta);
		assertEquals(0, pauseOverlay.renderCallCount);
	}

	@Test
	void isPausedRendersPauseOverlay() {
		setPrivateField(gameScreen, "isPaused", true);

		gameScreen.render(0.5f);

		assertEquals(0, gameWorld.stepCallCount);
		assertEquals(0, gameView.updateCameraCallCount);
		assertEquals(0, gameView.renderCallCount);
		assertEquals(1, pauseOverlay.renderCallCount);
	}

	@Test
	void escapeRendersPauseOverlay() {
		recordingInput.escapeJustPressed = true;

		gameScreen.render(0.016f);

		assertEquals(0, gameWorld.stepCallCount);
		assertEquals(0, gameView.renderCallCount);
		assertEquals(1, pauseOverlay.renderCallCount);
		assertTrue((Boolean) getPrivateField(gameScreen, "isPaused"));
	}

	@Test
	void resizeDelegatesToGameView() {
		gameScreen.resize(1024, 768);

		assertEquals(1, gameView.resizeCallCount);
		assertEquals(1024, gameView.lastResizeWidth);
		assertEquals(768, gameView.lastResizeHeight);
	}

	@Test
	void hideClearsInputProcessor() {
		recordingInput.inputProcessor = new DummyInputProcessor();

		gameScreen.hide();

		assertNull(recordingInput.inputProcessor);
	}

	@Test
	void pauseAndResumeDoNotThrow() {
		assertDoesNotThrow(() -> gameScreen.pause());
		assertDoesNotThrow(() -> gameScreen.resume());
		assertFalse((Boolean) getPrivateField(gameScreen, "isPaused"));
	}

	private static Input createInputProxy(RecordingInput state) {
		return (Input) Proxy.newProxyInstance(
				Input.class.getClassLoader(),
				new Class<?>[]{ Input.class },
				(proxy, method, args) -> {
					String name = method.getName();
					if (name.equals("isKeyJustPressed")) {
						int key = (int) args[0];
						return key == Input.Keys.ESCAPE && state.escapeJustPressed;
					}
					if (name.equals("setInputProcessor")) {
						state.inputProcessor = (InputProcessor) args[0];
						return null;
					}
					if (name.equals("getInputProcessor")) {
						return state.inputProcessor;
					}
					return defaultValue(method.getReturnType());
				}
		);
	}

	private static Graphics createGraphicsProxy() {
		return (Graphics) Proxy.newProxyInstance(
				Graphics.class.getClassLoader(),
				new Class<?>[]{ Graphics.class },
				(proxy, method, args) -> {
					if (Objects.equals(method.getName(), "getDeltaTime")) {
						return 1f / 60f;
					}
					if (Objects.equals(method.getName(), "getWidth")) {
						return 1280;
					}
					if (Objects.equals(method.getName(), "getHeight")) {
						return 768;
					}
					if (Objects.equals(method.getName(), "getBackBufferScale")) {
						return 1f;
					}
					return defaultValue(method.getReturnType());
				}
		);
	}

	private static Object defaultValue(Class<?> returnType) {
		if (returnType == void.class) return null;
		if (returnType == boolean.class) return false;
		if (returnType == byte.class) return (byte) 0;
		if (returnType == short.class) return (short) 0;
		if (returnType == int.class) return 0;
		if (returnType == long.class) return 0L;
		if (returnType == float.class) return 0f;
		if (returnType == double.class) return 0d;
		if (returnType == char.class) return '\0';
		return null;
	}

	private static Object getPrivateField(Object target, String fieldName) {
		try {
			Field field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(target);
		} catch (ReflectiveOperationException e) {
			throw new AssertionError("Failed to read field: " + fieldName, e);
		}
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

	private static class RecordingInput {
		boolean escapeJustPressed;
		InputProcessor inputProcessor;
	}

	private static class RecordingGameWorld extends GameWorld {
		int stepCallCount = 0;
		float lastDelta = -1f;

		RecordingGameWorld(Player player) {
			super(new Room(5, 5), player);
		}

		@Override
		public void step(double deltaTime) {
			stepCallCount++;
			lastDelta = (float) deltaTime;
		}
	}

	private static class RecordingGameView extends GameView {
		int updateCameraCallCount = 0;
		int renderCallCount = 0;
		int resizeCallCount = 0;
		float lastCameraX = -1f;
		float lastCameraY = -1f;
		float lastRenderDelta = -1f;
		int lastResizeWidth = -1;
		int lastResizeHeight = -1;

		@Override
		public void updateCamera(float playerX, float playerY) {
			updateCameraCallCount++;
			lastCameraX = playerX;
			lastCameraY = playerY;
		}

		@Override
		public void render(float deltaTime, GameWorld world) {
			renderCallCount++;
			lastRenderDelta = deltaTime;
		}

		@Override
		public void resize(int width, int height) {
			resizeCallCount++;
			lastResizeWidth = width;
			lastResizeHeight = height;
		}
	}

	private static class RecordingPauseOverlay extends PauseOverlay {
		int renderCallCount = 0;

		RecordingPauseOverlay(Game game) {
			super(game, () -> {
			});
		}

		@Override
		public void render() {
			renderCallCount++;
		}
	}

	private static class DummyGame extends Game {
		@Override
		public void create() {
		}
	}

	private static class DummyInputProcessor implements InputProcessor {
		@Override
		public boolean keyDown(int keycode) {
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(float amountX, float amountY) {
			return false;
		}
	}
	
}
