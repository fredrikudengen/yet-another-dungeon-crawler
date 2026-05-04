package inf112.dungeoncrawler.model.entities.powerups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.Test;

public class AttackPowerupTest {

	private static final SoundService NO_SOUND = new SoundService() {
		@Override
		public void play(SoundEffect effect) { /* test */ }

		@Override
		public void dispose() { /* test */ }
	};

	@Test
	void onCollisionIncreasesDamageByThree() {
		Player player = new Player(100, 100, NO_SOUND);
		AttackPowerup powerup = new AttackPowerup(100, 100, NO_SOUND);
		float initialDamage = player.getDamage();

		powerup.onCollision(player);

		assertEquals(initialDamage + 3, player.getDamage());
	}

	@Test
	void stepPickupAppliesBuffAndMarksPickedUp() {
		Player player = new Player(100, 100, NO_SOUND);
		Room room = new Room(10, 10);
		GameWorld world = new GameWorld(room, player);
		AttackPowerup powerup = new AttackPowerup(100, 100, NO_SOUND);

		powerup.step(world, 0.0);

		assertTrue(powerup.isPickedUp());
		assertEquals(4f, player.getDamage());
	}

	@Test
	void stepAfterDurationRemovesBuffAndMarksDead() {
		Player player = new Player(100, 100, NO_SOUND);
		Room room = new Room(10, 10);
		GameWorld world = new GameWorld(room, player);
		AttackPowerup powerup = new AttackPowerup(100, 100, NO_SOUND);

		powerup.step(world, 0.0);
		powerup.step(world, 10.0);

		assertEquals(1f, player.getDamage());
		assertFalse(powerup.isAlive());
	}
}
