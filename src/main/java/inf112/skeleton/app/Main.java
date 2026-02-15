package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import inf112.skeleton.view.Menu.TitleScreen;
import org.lwjgl.system.Configuration;

public class Main extends Game {

	public static void main(String[] args) {
		Main.class.getClassLoader().setDefaultAssertionStatus(true);

		// MacOS GLFW workaround
		if (SharedLibraryLoader.os == Os.MacOsX) {
			Configuration.GLFW_LIBRARY_NAME.set("glfw_async");
		}

		// Setup window
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		cfg.setForegroundFPS(60);

		Main game = new Main();
		new Lwjgl3Application(game, cfg);
	}

	@Override
	public void create() {
		// Start with title screen
		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		// Screen disposal is handled by Game class
	}
}