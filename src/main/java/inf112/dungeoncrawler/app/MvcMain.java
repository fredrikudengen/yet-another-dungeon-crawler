package inf112.dungeoncrawler.app;

import org.lwjgl.system.Configuration;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;

public class MvcMain {
	public static void main(String[] args) {
		// enable assertion checking by default
		MvcMain.class.getClassLoader().setDefaultAssertionStatus(true);

		// Workaround for Mac OS problem – alternatively, we could start the JVM with
		// the -XstartOnFirstThread option.
		// See https://javadoc.lwjgl.org/org/lwjgl/glfw/package-summary.html for
		// explanation
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
}
