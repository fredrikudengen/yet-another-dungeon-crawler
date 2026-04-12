package inf112.dungeoncrawler.model.entities.powerups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundPlayer;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.Test;

public class PowerupTest {

	private static final SoundService NO_SOUND = new SoundService() {
		@Override
		public void play(SoundEffect effect) { /* ingenting */ }

		@Override
		public void dispose() { /* ingenting */ }
	};

	private static class TestPowerup extends Powerup {
		TestPowerup(float x, float y, SoundService sound) {
			super(x, y, sound);
		}

		@Override
		public String getBaseTexturePath() {
			return "";
		}
	}

	@Test
	void constructorSetsDefaultSizeAndPickedUpState() {
		TestPowerup powerup = new TestPowerup(5, 7, NO_SOUND);

		assertEquals(64f, powerup.getWidth());
		assertEquals(64f, powerup.getHeight());
		assertFalse(powerup.isPickedUp());
	}
}
