package inf112.dungeoncrawler.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import inf112.dungeoncrawler.view.screens.TitleScreen;
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
		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}