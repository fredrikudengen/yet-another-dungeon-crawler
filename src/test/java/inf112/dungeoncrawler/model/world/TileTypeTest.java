package inf112.dungeoncrawler.model.world;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTypeTest {

    @Test
    void emptyHasCorrectTextureName() {
        assertEquals("tiles/empty.png", TileType.EMPTY.getTextureName());
    }

    @Test
    void floorHasCorrectTextureName() {
        assertEquals("tiles/wall2.png", TileType.FLOOR.getTextureName());
    }

    @Test
    void wallHasCorrectTextureName() {
        assertEquals("tiles/wall3.png", TileType.WALL.getTextureName());
    }

    @Test
    void emptyBlocksMovement() {
        assertTrue(TileType.EMPTY.blocksMovement());
    }

    @Test
    void floorDoesNotBlockMovement() {
        assertFalse(TileType.FLOOR.blocksMovement());
    }

    @Test
    void wallBlocksMovement() {
        assertTrue(TileType.WALL.blocksMovement());
    }

    @Test
    void doorClosedBlocksMovement() {
        assertTrue(TileType.DOOR_CLOSED.blocksMovement());
    }

    @Test
    void doorOpenDoesNotBlockMovement() {
        assertFalse(TileType.DOOR_OPEN.blocksMovement());
    }

    @Test
    void allTileTypesHaveNonNullTextureName() {
        for (TileType type : TileType.values()) {
            assertNotNull(type.getTextureName(), type + " has null texture name");
        }
    }
}