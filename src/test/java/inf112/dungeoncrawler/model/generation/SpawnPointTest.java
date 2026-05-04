package inf112.dungeoncrawler.model.generation;

import org.junit.jupiter.api.Test;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class SpawnPointTest {

	@Test
	void constructorStoresCoordinatesAndType() {
		SpawnPoint sp = new SpawnPoint(7, 9, SpawnPoint.Type.PLAYER);
		assertEquals(7, sp.getX());
		assertEquals(9, sp.getY());
		assertEquals(SpawnPoint.Type.PLAYER, sp.getType());
	}

	@Test
	void constructorSupportsNegativeCoordinates() {
		SpawnPoint sp = new SpawnPoint(-3, -11, SpawnPoint.Type.SLOWENEMY);
		assertEquals(-3, sp.getX());
		assertEquals(-11, sp.getY());
		assertEquals(SpawnPoint.Type.SLOWENEMY, sp.getType());
	}

	@Test
	void typeEnumContainsAllExpectedTypes() {
		EnumSet<SpawnPoint.Type> types = EnumSet.allOf(SpawnPoint.Type.class);
		assertTrue(types.contains(SpawnPoint.Type.FASTENEMY));
		assertTrue(types.contains(SpawnPoint.Type.NORMALENEMY));
		assertTrue(types.contains(SpawnPoint.Type.SLOWENEMY));
		assertTrue(types.contains(SpawnPoint.Type.SPEEDPOWERUP));
		assertTrue(types.contains(SpawnPoint.Type.ATTACKPOWERUP));
		assertTrue(types.contains(SpawnPoint.Type.HEALTHPOWERUP));
		assertTrue(types.contains(SpawnPoint.Type.PLAYER));
		assertTrue(types.contains(SpawnPoint.Type.DOOR));
	}
}