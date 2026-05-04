package inf112.dungeoncrawler.model.entities.powerups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.Test;

public class HealthPowerupTest {

	private static final SoundService NO_SOUND = new SoundService() {
		@Override
		public void play(SoundEffect effect) { /* test */ }

		@Override
		public void dispose() { /* test */ }
	};

	@Test
	void getBaseTexturePathReturnsHealthPowerupPath() {
		HealthPowerup powerup = new HealthPowerup(0, 0, NO_SOUND);

		assertEquals("powerups/Life.png", powerup.getBaseTexturePath());
	}

	@Test
	void onCollisionIncreaseHealthAndPowerupDead() {
		Player player = new Player(100, 100, NO_SOUND);
		HealthPowerup powerup = new HealthPowerup(100, 100, NO_SOUND);
		int initialHealth = player.getHealth();

		powerup.onCollision(player);

		assertEquals(initialHealth + 1, player.getHealth());
		assertFalse(powerup.isAlive());
	}

	@Test
	void stepWithCollisionAppliesHealthIncrease() {
		Player player = new Player(100, 100, NO_SOUND);
		Room room = new Room(10, 10);
		GameWorld world = new GameWorld(room, player);
		HealthPowerup powerup = new HealthPowerup(100, 100, NO_SOUND);
		int initialHealth = player.getHealth();

		powerup.step(world, 0.016);

		assertEquals(initialHealth + 1, player.getHealth());
		assertFalse(powerup.isAlive());
	}
}
