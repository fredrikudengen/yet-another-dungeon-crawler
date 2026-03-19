package inf112.dungeoncrawler.model.world;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TileTypeTest {

	@Test
	void testFloorTextureNameIsCorrect() {
		assertEquals("Floor_wall_door/floor2.png", TileType.FLOOR.getTextureName());
	}

	@Test
	void testWallTextureNameIsCorrect() {
		assertEquals("Floor_wall_door/wall2.png", TileType.WALL.getTextureName());
	}

	@Test
	void testFloorDoesNotBlockMovement() {
		assertFalse(TileType.FLOOR.blocksMovement());
	}

	@Test
	void testWallBlocksMovement() {
		assertTrue(TileType.WALL.blocksMovement());
	}

	@Test
	void testEnumContainsExpectedTileTypes() {
		assertArrayEquals(new TileType[] { TileType.FLOOR, TileType.WALL }, TileType.values());
	}
}
