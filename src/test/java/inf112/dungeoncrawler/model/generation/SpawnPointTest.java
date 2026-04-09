package inf112.dungeoncrawler.model.generation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SpawnPointTest {

	@Test
	void constructorStoresCoordinatesAndType() {
		SpawnPoint spawnPoint = new SpawnPoint(7, 9, SpawnPoint.Type.PLAYER);

		assertEquals(7, spawnPoint.x);
		assertEquals(9, spawnPoint.y);
		assertEquals(SpawnPoint.Type.PLAYER, spawnPoint.type);
	}

	@Test
	void constructorSupportsNegativeCoordinates() {
		SpawnPoint spawnPoint = new SpawnPoint(-3, -11, SpawnPoint.Type.SLOWENEMY);

		assertEquals(-3, spawnPoint.x);
		assertEquals(-11, spawnPoint.y);
		assertEquals(SpawnPoint.Type.SLOWENEMY, spawnPoint.type);
	}

	@Test
	void typeEnumContainsExpectedValuesInExpectedOrder() {
		SpawnPoint.Type[] types = SpawnPoint.Type.values();

		assertEquals(7, types.length);
		assertEquals(SpawnPoint.Type.FASTENEMY, types[0]);
		assertEquals(SpawnPoint.Type.NORMALENEMY, types[1]);
		assertEquals(SpawnPoint.Type.SLOWENEMY, types[2]);
		assertEquals(SpawnPoint.Type.SPEEDPOWERUP, types[3]);
		assertEquals(SpawnPoint.Type.ATTACKPOWERUP, types[4]);
		assertEquals(SpawnPoint.Type.HEALTHPOWERUP, types[5]);
		assertEquals(SpawnPoint.Type.PLAYER, types[6]);
	}
}
