package inf112.dungeoncrawler.model.entities.powerups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.sound.SoundPlayer;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.Test;

public class PowerupTest {

	private static class TestPowerup extends Powerup {
		TestPowerup(float x, float y, SoundService sound) {
			super(x, y, sound);
		}
	}

	@Test
	void constructorSetsDefaultSizeAndPickedUpState() {
		SoundService noSound = effect -> {};
		TestPowerup powerup = new TestPowerup(5, 7, noSound);

		assertEquals(64f, powerup.getWidth());
		assertEquals(64f, powerup.getHeight());
		assertFalse(powerup.isPickedUp());
	}

	@Test
	void stepPlaysPickupSoundWhenCollidingWithPlayer() {
		final int[] calls = {0};
		final SoundPlayer.SoundEffect[] lastEffect = {null};
		SoundService recordingSound = effect -> {
			calls[0]++;
			lastEffect[0] = effect;
		};

		Player player = new Player(100, 100, recordingSound);
		Room room = new Room(10, 10);
		GameWorld world = new GameWorld(room, player);
		TestPowerup powerup = new TestPowerup(100, 100, recordingSound);

		powerup.step(world, 0.016);

		assertEquals(1, calls[0]);
		assertEquals(SoundPlayer.SoundEffect.POWERUP_PICKUP, lastEffect[0]);
	}

	@Test
	void stepDoesNotPlayPickupSoundWhenNotCollidingWithPlayer() {
		final int[] calls = {0};
		SoundService recordingSound = effect -> calls[0]++;

		Player player = new Player(100, 100, recordingSound);
		Room room = new Room(10, 10);
		GameWorld world = new GameWorld(room, player);
		TestPowerup powerup = new TestPowerup(1000, 1000, recordingSound);

		powerup.step(world, 0.016);

		assertEquals(0, calls[0]);
	}

	@Test
	void getBaseTexturePathReturnsNullInBaseClass() {
		SoundService noSound = effect -> {};
		TestPowerup powerup = new TestPowerup(0, 0, noSound);

		assertNull(powerup.getBaseTexturePath());
	}
}
