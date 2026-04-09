package inf112.dungeoncrawler.model.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.generation.SpawnPoint;
import inf112.dungeoncrawler.model.world.GameWorld;
import org.junit.jupiter.api.Test;

public class EnemyFactoryTest {

	private static class TestEntity extends Entity {
		TestEntity(float x, float y) {
			super(x, y);
		}

		@Override
		public void step(GameWorld world, double deltaTime) {
		}

		@Override
		public String getBaseTexturePath() {
			return "test";
		}
	}

	@Test
	void spawnReturnsEntityFromRegisteredSpawner() {
		EntityFactory factory = new EntityFactory();
		factory.register(SpawnPoint.Type.FASTENEMY, TestEntity::new);
		SpawnPoint spawnPoint = new SpawnPoint(4, 9, SpawnPoint.Type.FASTENEMY);

		Entity entity = factory.spawn(spawnPoint);

		assertNotNull(entity);
		assertEquals(4f, entity.getX());
		assertEquals(9f, entity.getY());
	}

	@Test
	void spawnUsesLatestRegisteredSpawnerForType() {
		EntityFactory factory = new EntityFactory();
		factory.register(SpawnPoint.Type.NORMALENEMY, (x, y) -> new TestEntity(x + 1, y + 1));
		factory.register(SpawnPoint.Type.NORMALENEMY, (x, y) -> new TestEntity(x + 2, y + 3));
		SpawnPoint spawnPoint = new SpawnPoint(10, 20, SpawnPoint.Type.NORMALENEMY);

		Entity entity = factory.spawn(spawnPoint);

		assertEquals(12f, entity.getX());
		assertEquals(23f, entity.getY());
	}

	@Test
	void spawnWithoutRegisteredTypeThrowsNullPointerException() {
		EntityFactory factory = new EntityFactory();
		SpawnPoint spawnPoint = new SpawnPoint(1, 1, SpawnPoint.Type.HEALTHPOWERUP);

		assertThrows(NullPointerException.class, () -> factory.spawn(spawnPoint));
	}
}
