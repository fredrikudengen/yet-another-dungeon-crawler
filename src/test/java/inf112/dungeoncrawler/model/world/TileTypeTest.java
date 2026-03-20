package inf112.dungeoncrawler.model.world;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TileTypeTest {

    @Test
    void testEmptyTextureNameIsCorrect() {
        assertEquals("tiles/empty.png", TileType.EMPTY.getTextureName());
    }

    @Test
    void testFloorTextureNameIsCorrect() {
        assertEquals("tiles/wall2.png", TileType.FLOOR.getTextureName());
    }

    @Test
    void testWallTextureNameIsCorrect() {
        assertEquals("tiles/wall3.png", TileType.WALL.getTextureName());
    }

    @Test
    void testEmptyBlocksMovement() {
        assertTrue(TileType.EMPTY.blocksMovement(), "EMPTY should block movement");
    }

    @Test
    void testFloorDoesNotBlockMovement() {
        assertFalse(TileType.FLOOR.blocksMovement(), "FLOOR should not block movement");
    }

    @Test
    void testWallBlocksMovement() {
        assertTrue(TileType.WALL.blocksMovement(), "WALL should block movement");
    }

    @Test
    void testEnumContainsAllTileTypes() {
        TileType[] expected = {TileType.EMPTY, TileType.FLOOR, TileType.WALL};
        assertArrayEquals(expected, TileType.values());
    }

    @Test
    void testTileTypeCount() {
        assertEquals(3, TileType.values().length);
    }

    @Test
    void testTextureNamesAreNotNull() {
        assertNotNull(TileType.EMPTY.getTextureName());
        assertNotNull(TileType.FLOOR.getTextureName());
        assertNotNull(TileType.WALL.getTextureName());
    }
}