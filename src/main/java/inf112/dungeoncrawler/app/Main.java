package inf112.dungeoncrawler.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import inf112.dungeoncrawler.sound.SoundPlayer;
import inf112.dungeoncrawler.sound.SoundService;
import inf112.dungeoncrawler.view.screens.TitleScreen;
import org.lwjgl.system.Configuration;

public class Main extends Game {

	private SoundService sound;

	public static void main(String[] args) {
		Main.class.getClassLoader().setDefaultAssertionStatus(true);

		if (SharedLibraryLoader.os == Os.MacOsX) {
			Configuration.GLFW_LIBRARY_NAME.set("glfw_async");
		}

		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		cfg.setForegroundFPS(60);

		new Lwjgl3Application(new Main(), cfg);
	}

	@Override
	public void create() {
		sound = new SoundPlayer();
		setScreen(new TitleScreen(this, sound));
	}

	@Override
	public void dispose() {
		sound.dispose();
		super.dispose();
	}
}