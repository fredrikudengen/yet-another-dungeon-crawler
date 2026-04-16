package inf112.dungeoncrawler.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.Test;

public class NormalEnemyTest {

	private static final SoundService NO_SOUND = new SoundService() {
		@Override
		public void play(SoundEffect effect) { /* ingenting */ }

		@Override
		public void dispose() { /* ingenting */ }
	};

	@Test
	void constructorSetsExpectedStats() {
		Player trackingPlayer = new Player(300, 300, NO_SOUND);
		NormalEnemy enemy = new NormalEnemy(11, 21, trackingPlayer);

		assertEquals(11f, enemy.getX());
		assertEquals(21f, enemy.getY());
		assertEquals(2f, enemy.getDamage());
		assertEquals(2, enemy.getHealth());
		assertEquals(150, enemy.getSpeed());
		assertEquals(64f, enemy.getWidth());
		assertEquals(64f, enemy.getHeight());
	}

	@Test
	void getBaseTexturePathReturnsNormalEnemyPath() {
		Player trackingPlayer = new Player(300, 300, NO_SOUND);
		NormalEnemy enemy = new NormalEnemy(11, 21, trackingPlayer);

		assertEquals("enemy/normal/enemy_normal_", enemy.getBaseTexturePath());
	}

	@Test
	void onCollisionDealsExpectedDamage() {
		Player trackingPlayer = new Player(300, 300, NO_SOUND);
		NormalEnemy enemy = new NormalEnemy(11, 21, trackingPlayer);
		Player target = new Player(11, 21, NO_SOUND);
		int initialHealth = target.getHealth();

		enemy.onCollision(target);

		assertEquals(initialHealth - 2, target.getHealth());
	}
}
