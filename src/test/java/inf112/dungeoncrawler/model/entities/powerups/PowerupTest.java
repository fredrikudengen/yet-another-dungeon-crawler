package inf112.dungeoncrawler.model.entities.powerups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.Test;

public class PowerupTest {

	private static final SoundService NO_SOUND = new SoundService() {
		@Override
		public void play(SoundEffect effect) { /* test */ }

		@Override
		public void dispose() { /* test */ }
	};

	private static class TestPowerup extends Powerup {
		TestPowerup(float x, float y, SoundService sound) {
			super(x, y, sound);
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
