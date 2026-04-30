package inf112.dungeoncrawler.model.world;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTypeTest {

    @Test
    void empty_hasCorrectTextureName() {
        assertEquals("tiles/empty.png", TileType.EMPTY.getTextureName());
    }

    @Test
    void floor_hasCorrectTextureName() {
        assertEquals("tiles/wall2.png", TileType.FLOOR.getTextureName());
    }

    @Test
    void wall_hasCorrectTextureName() {
        assertEquals("tiles/wall3.png", TileType.WALL.getTextureName());
    }

    @Test
    void empty_blocksMovement() {
        assertTrue(TileType.EMPTY.blocksMovement());
    }

    @Test
    void floor_doesNotBlockMovement() {
        assertFalse(TileType.FLOOR.blocksMovement());
    }

    @Test
    void wall_blocksMovement() {
        assertTrue(TileType.WALL.blocksMovement());
    }

    @Test
    void doorClosed_blocksMovement() {
        assertTrue(TileType.DOOR_CLOSED.blocksMovement());
    }

    @Test
    void doorOpen_doesNotBlockMovement() {
        assertFalse(TileType.DOOR_OPEN.blocksMovement());
    }

    @Test
    void allTileTypes_haveNonNullTextureName() {
        for (TileType type : TileType.values()) {
            assertNotNull(type.getTextureName(), type + " has null texture name");
        }
    }
}