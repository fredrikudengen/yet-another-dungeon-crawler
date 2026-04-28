package inf112.dungeoncrawler.model.world;

import inf112.dungeoncrawler.model.generation.SpawnPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;
import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private static final int ROOM_WIDTH  = 10;
    private static final int ROOM_HEIGHT = 8;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room(ROOM_WIDTH, ROOM_HEIGHT);
    }

    @Test
    void constructor_setsCorrectDimensions() {
        assertEquals(ROOM_WIDTH,  room.getWidth());
        assertEquals(ROOM_HEIGHT, room.getHeight());
    }

    @Test
    void constructor_fillsWithFloorTiles() {
        for (int x = 0; x < ROOM_WIDTH; x++) {
            for (int y = 0; y < ROOM_HEIGHT; y++) {
                assertEquals(TileType.FLOOR, room.getTileAt(x, y));
            }
        }
    }

    @Test
    void getTileAt_outOfBounds_returnsWall() {
        assertEquals(TileType.WALL, room.getTileAt(-1, 0));
        assertEquals(TileType.WALL, room.getTileAt(0, -1));
        assertEquals(TileType.WALL, room.getTileAt(ROOM_WIDTH, 0));
        assertEquals(TileType.WALL, room.getTileAt(0, ROOM_HEIGHT));
    }

    @Test
    void setTileAt_updatesTile() {
        room.setTileAt(3, 2, TileType.WALL);
        assertEquals(TileType.WALL,  room.getTileAt(3, 2));
        assertEquals(TileType.FLOOR, room.getTileAt(2, 2));
    }

    @Test
    void setTileAt_outOfBounds_doesNotThrow() {
        assertDoesNotThrow(() -> room.setTileAt(-1, 0, TileType.WALL));
        assertDoesNotThrow(() -> room.setTileAt(ROOM_WIDTH, 0, TileType.WALL));
        assertEquals(TileType.FLOOR, room.getTileAt(0, 0));
    }

    @Test
    void isBlocked_floorTile_returnsFalse() {
        assertFalse(room.isBlocked(1.5f * TILE_SIZE, 1.5f * TILE_SIZE, 16, 16));
    }

    @Test
    void isBlocked_wallTile_returnsTrue() {
        room.setTileAt(2, 3, TileType.WALL);
        assertTrue(room.isBlocked(2 * TILE_SIZE + 5, 3 * TILE_SIZE + 5, 16, 16));
    }

    @Test
    void isBlocked_checksAllFourCorners() {
        room.setTileAt(2, 2, TileType.WALL);
        assertTrue(room.isBlocked(2 * TILE_SIZE - 10, 2 * TILE_SIZE - 10, 20, 20));
    }

    @Test
    void isBlocked_outsideRoom_returnsTrue() {
        assertTrue(room.isBlocked(-TILE_SIZE, -TILE_SIZE, 16, 16));
        assertTrue(room.isBlocked(ROOM_WIDTH * TILE_SIZE + 10, ROOM_HEIGHT * TILE_SIZE + 10, 16, 16));
    }

    @Test
    void isBlocked_doorClosed_returnsTrue() {
        room.setTileAt(2, 2, TileType.DOOR_CLOSED);
        assertTrue(room.isBlocked(2 * TILE_SIZE + 5, 2 * TILE_SIZE + 5, 16, 16));
    }

    @Test
    void isBlocked_doorOpen_returnsFalse() {
        room.setTileAt(2, 2, TileType.DOOR_OPEN);
        assertFalse(room.isBlocked(2 * TILE_SIZE + 5, 2 * TILE_SIZE + 5, 16, 16));
    }

    @Test
    void isBlocked_fullTileOnRightEdgeOpenDoor_returnsFalse() {
        room.setTileAt(ROOM_WIDTH - 1, 3, TileType.DOOR_OPEN);
        assertFalse(room.isBlocked((ROOM_WIDTH - 1) * TILE_SIZE, 3 * TILE_SIZE, TILE_SIZE, TILE_SIZE));
    }

    @Test
    void isBlocked_fullTileOnTopEdgeOpenDoor_returnsFalse() {
        room.setTileAt(4, ROOM_HEIGHT - 1, TileType.DOOR_OPEN);
        assertFalse(room.isBlocked(4 * TILE_SIZE, (ROOM_HEIGHT - 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE));
    }

    @Test
    void isBlocked_partialNegativePosition_returnsTrue() {
        assertTrue(room.isBlocked(-1, TILE_SIZE, 16, 16));
        assertTrue(room.isBlocked(TILE_SIZE, -1, 16, 16));
    }

    @Test
    void openDoors_convertsDoorClosedToDoorOpen() {
        room.setTileAt(1, 1, TileType.DOOR_CLOSED);
        room.setTileAt(3, 3, TileType.DOOR_CLOSED);
        room.openDoors();
        assertEquals(TileType.DOOR_OPEN, room.getTileAt(1, 1));
        assertEquals(TileType.DOOR_OPEN, room.getTileAt(3, 3));
    }

    @Test
    void openDoors_convertsDirectionalDoorsToMatchingOpenVariant() {
        room.setTileAt(1, 1, TileType.DOOR_LEFT_WALL_CLOSED);
        room.setTileAt(2, 1, TileType.DOOR_RIGHT_WALL_CLOSED);
        room.setTileAt(3, 1, TileType.DOOR_TOP_CLOSED);
        room.setTileAt(4, 1, TileType.DOOR_BOTTOM_CLOSED);

        room.openDoors();

        assertEquals(TileType.DOOR_LEFT_WALL_OPEN, room.getTileAt(1, 1));
        assertEquals(TileType.DOOR_RIGHT_WALL_OPEN, room.getTileAt(2, 1));
        assertEquals(TileType.DOOR_TOP_OPEN, room.getTileAt(3, 1));
        assertEquals(TileType.DOOR_BOTTOM_OPEN, room.getTileAt(4, 1));
    }

    @Test
    void openDoors_doesNotAffectOtherTiles() {
        room.setTileAt(1, 1, TileType.DOOR_CLOSED);
        room.setTileAt(2, 2, TileType.WALL);
        room.openDoors();
        assertEquals(TileType.WALL,  room.getTileAt(2, 2));
        assertEquals(TileType.FLOOR, room.getTileAt(0, 0));
    }

    @Test
    void addSpawnPoint_appearsInList() {
        SpawnPoint sp1 = new SpawnPoint(100, 200, SpawnPoint.Type.PLAYER);
        SpawnPoint sp2 = new SpawnPoint(300, 400, SpawnPoint.Type.FASTENEMY);
        room.addSpawnPoint(sp1);
        room.addSpawnPoint(sp2);

        List<SpawnPoint> spawnPoints = room.getSpawnPoints();
        assertEquals(2, spawnPoints.size());
        assertTrue(spawnPoints.contains(sp1));
        assertTrue(spawnPoints.contains(sp2));
    }
}