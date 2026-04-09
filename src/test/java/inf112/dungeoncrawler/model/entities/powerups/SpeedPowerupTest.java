package inf112.dungeoncrawler.model.entities.powerups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.Test;

public class SpeedPowerupTest {

	private static final SoundService NO_SOUND = effect -> {};

	@Test
	void getBaseTexturePathReturnsSpeedPowerupPath() {
		SpeedPowerup powerup = new SpeedPowerup(0, 0, NO_SOUND);

		assertEquals("powerups/faster.png", powerup.getBaseTexturePath());
	}

	@Test
	void onCollisionIncreasesSpeedByOneHundred() {
		Player player = new Player(100, 100, NO_SOUND);
		SpeedPowerup powerup = new SpeedPowerup(100, 100, NO_SOUND);
		int initialSpeed = player.getSpeed();

		powerup.onCollision(player);

		assertEquals(initialSpeed + 100, player.getSpeed());
	}

	@Test
	void stepPickupAppliesBuffAndMarksPickedUp() {
		Player player = new Player(100, 100, NO_SOUND);
		Room room = new Room(10, 10);
		GameWorld world = new GameWorld(room, player);
		SpeedPowerup powerup = new SpeedPowerup(100, 100, NO_SOUND);

		powerup.step(world, 0.0);

		assertTrue(powerup.isPickedUp());
		assertEquals(400, player.getSpeed());
	}

	@Test
	void stepAfterDurationRemovesBuffAndMarksDead() {
		Player player = new Player(100, 100, NO_SOUND);
		Room room = new Room(10, 10);
		GameWorld world = new GameWorld(room, player);
		SpeedPowerup powerup = new SpeedPowerup(100, 100, NO_SOUND);

		powerup.step(world, 0.0);
		powerup.step(world, 10.0);

		assertEquals(300, player.getSpeed());
		assertFalse(powerup.isAlive());
	}
}
