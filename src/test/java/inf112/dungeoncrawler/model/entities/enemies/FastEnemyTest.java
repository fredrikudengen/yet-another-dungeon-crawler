package inf112.dungeoncrawler.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.Test;

public class FastEnemyTest {

	private static final SoundService NO_SOUND = new SoundService() {
		@Override
		public void play(SoundEffect effect) { /* test */ }

		@Override
		public void dispose() { /* test */ }
	};

	@Test
	void constructorSetsExpectedStats() {
		Player trackingPlayer = new Player(300, 300, NO_SOUND);
		FastEnemy enemy = new FastEnemy(10, 20, trackingPlayer);

		assertEquals(10f, enemy.getX());
		assertEquals(20f, enemy.getY());
		assertEquals(1f, enemy.getDamage());
		assertEquals(2, enemy.getHealth());
		assertEquals(240, enemy.getSpeed());
		assertEquals(64f, enemy.getWidth());
		assertEquals(64f, enemy.getHeight());
	}

	@Test
	void getBaseTexturePathReturnsFastEnemyPath() {
		Player trackingPlayer = new Player(300, 300, NO_SOUND);
		FastEnemy enemy = new FastEnemy(10, 20, trackingPlayer);

		assertEquals("enemy/fast/enemy_", enemy.getBaseTexturePath());
	}

	@Test
	void onCollisionDealsExpectedDamage() {
		Player trackingPlayer = new Player(300, 300, NO_SOUND);
		FastEnemy enemy = new FastEnemy(10, 20, trackingPlayer);
		Player target = new Player(10, 20, NO_SOUND);
		int initialHealth = target.getHealth();

		enemy.onCollision(target);

		assertEquals(initialHealth - 1, target.getHealth());
	}
}
