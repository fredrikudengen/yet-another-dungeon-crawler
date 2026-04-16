package inf112.dungeoncrawler.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.Test;

public class SlowEnemyTest {

	private static final SoundService NO_SOUND = new SoundService() {
		@Override
		public void play(SoundEffect effect) { /* ingenting */ }

		@Override
		public void dispose() { /* ingenting */ }
	};

	@Test
	void constructorSetsExpectedStats() {
		Player trackingPlayer = new Player(300, 300, NO_SOUND);
		SlowEnemy enemy = new SlowEnemy(12, 22, trackingPlayer);

		assertEquals(12f, enemy.getX());
		assertEquals(22f, enemy.getY());
		assertEquals(3f, enemy.getDamage());
		assertEquals(3, enemy.getHealth());
		assertEquals(120, enemy.getSpeed());
		assertEquals(64f, enemy.getWidth());
		assertEquals(64f, enemy.getHeight());
	}

	@Test
	void getBaseTexturePathReturnsSlowEnemyPath() {
		Player trackingPlayer = new Player(300, 300, NO_SOUND);
		SlowEnemy enemy = new SlowEnemy(12, 22, trackingPlayer);

		assertEquals("enemy/slow/enemy_slow_", enemy.getBaseTexturePath());
	}

	@Test
	void onCollisionDealsExpectedDamage() {
		Player trackingPlayer = new Player(300, 300, NO_SOUND);
		SlowEnemy enemy = new SlowEnemy(12, 22, trackingPlayer);
		Player target = new Player(12, 22, NO_SOUND);
		int initialHealth = target.getHealth();

		enemy.onCollision(target);

		assertEquals(initialHealth - 3, target.getHealth());
	}
}
