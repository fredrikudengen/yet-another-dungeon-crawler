package inf112.dungeoncrawler.model.factories;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.generation.SpawnPoint;
import inf112.dungeoncrawler.model.world.GameWorld;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyFactoryTest {

	private static class TestEntity extends Entity {
		TestEntity(float x, float y) { super(x, y); }

		@Override public void step(GameWorld world, double deltaTime) {}
		@Override public String getBaseTexturePath() { return "test"; }
	}

	@Test
	void spawn_registeredType_returnsEntityAtCorrectPosition() {
		EntityFactory factory = new EntityFactory();
		factory.register(SpawnPoint.Type.FASTENEMY, TestEntity::new);

		Entity entity = factory.spawn(new SpawnPoint(4, 9, SpawnPoint.Type.FASTENEMY));

		assertNotNull(entity);
		assertEquals(4f, entity.getX());
		assertEquals(9f, entity.getY());
	}

	@Test
	void spawn_registeredTwice_usesLatestSpawner() {
		EntityFactory factory = new EntityFactory();
		factory.register(SpawnPoint.Type.NORMALENEMY, (x, y) -> new TestEntity(x + 1, y + 1));
		factory.register(SpawnPoint.Type.NORMALENEMY, (x, y) -> new TestEntity(x + 2, y + 3));

		Entity entity = factory.spawn(new SpawnPoint(10, 20, SpawnPoint.Type.NORMALENEMY));

		assertEquals(12f, entity.getX());
		assertEquals(23f, entity.getY());
	}

	@Test
	void spawn_unregisteredType_throwsIllegalArgumentException() {
		EntityFactory factory = new EntityFactory();
		assertThrows(IllegalArgumentException.class,
				() -> factory.spawn(new SpawnPoint(1, 1, SpawnPoint.Type.HEALTHPOWERUP)));
	}
}