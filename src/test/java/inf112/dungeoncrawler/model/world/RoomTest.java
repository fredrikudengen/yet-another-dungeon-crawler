package inf112.dungeoncrawler.model.world;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf112.dungeoncrawler.model.generation.SpawnPoint;
import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

class RoomTest {

    private Room room;
    private static final int ROOM_WIDTH = 10;
    private static final int ROOM_HEIGHT = 8;

    @BeforeEach
    void setUp() {
        room = new Room(ROOM_WIDTH, ROOM_HEIGHT);
    }

    @Test
    void testConstructorCreatesCorrectSize() {
        assertEquals(ROOM_WIDTH, room.getWidth());
        assertEquals(ROOM_HEIGHT, room.getHeight());
    }

    @Test
    void testConstructorFillsWithFloorTiles() {
        for (int x = 0; x < ROOM_WIDTH; x++) {
            for (int y = 0; y < ROOM_HEIGHT; y++) {
                assertEquals(TileType.FLOOR, room.getTileAt(x, y));
            }
        }
    }

    @Test
    void testGetTileAtReturnsWallForOutOfBounds() {
        assertEquals(TileType.WALL, room.getTileAt(-1, 0));
        assertEquals(TileType.WALL, room.getTileAt(0, -1));
        assertEquals(TileType.WALL, room.getTileAt(ROOM_WIDTH, 0));
        assertEquals(TileType.WALL, room.getTileAt(0, ROOM_HEIGHT));
        assertEquals(TileType.WALL, room.getTileAt(-5, ROOM_HEIGHT + 5));
    }

    @Test
    void testSetTileAtUpdatesTile() {
        room.setTileAt(3, 2, TileType.WALL);
        assertEquals(TileType.WALL, room.getTileAt(3, 2));
        assertEquals(TileType.FLOOR, room.getTileAt(2, 2)); 
    }

    @Test
    void testSetTileAtDoesNothingForOutOfBounds() {
        assertDoesNotThrow(() -> room.setTileAt(-1, 0, TileType.WALL));
        assertDoesNotThrow(() -> room.setTileAt(ROOM_WIDTH, 0, TileType.WALL));

        assertEquals(TileType.FLOOR, room.getTileAt(0, 0));
    }

    @Test
    void testIsBlockedWithFloorTile() {
        float x = 1.5f * TILE_SIZE;
        float y = 1.5f * TILE_SIZE;

        assertFalse(room.isBlocked(x, y, 16, 16));
    }

    @Test
    void testIsBlockedWithWallTile() {
        int wallX = 2;
        int wallY = 3;
        room.setTileAt(wallX, wallY, TileType.WALL);

        float x = wallX * TILE_SIZE + 5;
        float y = wallY * TILE_SIZE + 5;

        assertTrue(room.isBlocked(x, y, 16, 16));
    }

    @Test
    void testIsBlockedChecksAllFourCorners() {
        room.setTileAt(2, 2, TileType.WALL);

        float x = 2 * TILE_SIZE - 10; 
        float y = 2 * TILE_SIZE - 10;

        assertTrue(room.isBlocked(x, y, 20, 20));
    }

    @Test
    void testIsBlockedWithEntityOutsideRoom() {
        float x = -TILE_SIZE;
        float y = -TILE_SIZE;

        assertTrue(room.isBlocked(x, y, 16, 16));

        x = ROOM_WIDTH * TILE_SIZE + 10;
        y = ROOM_HEIGHT * TILE_SIZE + 10;

        assertTrue(room.isBlocked(x, y, 16, 16));
    }

    @Test
    void testIsBlockedAtExactTileBoundaries() {
        room.setTileAt(2, 2, TileType.WALL);

        float x = 2 * TILE_SIZE;
        float y = 2 * TILE_SIZE;

        assertTrue(room.isBlocked(x, y, 16, 16));
    }

    @Test
    void testAddAndGetSpawnPoints() {
        SpawnPoint sp1 = new SpawnPoint(100, 200, SpawnPoint.Type.PLAYER);
        SpawnPoint sp2 = new SpawnPoint(300, 400, SpawnPoint.Type.FASTENEMY);

        room.addSpawnPoint(sp1);
        room.addSpawnPoint(sp2);

        List<SpawnPoint> spawnPoints = room.getSpawnPoints();
        assertEquals(2, spawnPoints.size());
        assertTrue(spawnPoints.contains(sp1));
        assertTrue(spawnPoints.contains(sp2));
    }

    @Test
    void testGetTilesReturnsCorrectArray() {
        TileType[][] tiles = room.getTiles();
        assertNotNull(tiles);
        assertEquals(ROOM_WIDTH, tiles.length);
        assertEquals(ROOM_HEIGHT, tiles[0].length);

        tiles[3][4] = TileType.WALL;
        assertEquals(TileType.WALL, room.getTileAt(3, 4));
    }

    @Test
    void testIsBlockedWithDifferentEntitySizes() {
        room.setTileAt(2, 2, TileType.WALL);

        float x = 2 * TILE_SIZE - 8;
        float y = 2 * TILE_SIZE - 8;
        assertTrue(room.isBlocked(x, y, 16, 16));

        x = 2 * TILE_SIZE - 16;
        y = 2 * TILE_SIZE - 16;
        assertTrue(room.isBlocked(x, y, 32, 32));

        x = 2 * TILE_SIZE - 4;
        y = 2 * TILE_SIZE - 4;
        assertTrue(room.isBlocked(x, y, 8, 8));
    }

    @Test
    void testMultipleWallsBlockCorrectly() {
        room.setTileAt(2, 2, TileType.WALL);
        room.setTileAt(3, 2, TileType.WALL);
        room.setTileAt(2, 3, TileType.WALL);

        float x = 2.2f * TILE_SIZE;
        float y = 2.2f * TILE_SIZE;
        assertTrue(room.isBlocked(x, y, 20, 20));
    }

    @Test
    void testGetTileAtWithDifferentTileTypes() {
        room.setTileAt(0, 0, TileType.FLOOR);
        room.setTileAt(1, 0, TileType.WALL);

        assertEquals(TileType.FLOOR, room.getTileAt(0, 0));
        assertEquals(TileType.WALL, room.getTileAt(1, 0));
    }
}