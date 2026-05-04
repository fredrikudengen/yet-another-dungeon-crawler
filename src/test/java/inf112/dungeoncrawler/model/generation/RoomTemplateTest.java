package inf112.dungeoncrawler.model.generation;

import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.model.world.TileType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomTemplateTest {

	@Test
	void createRoom_hasExpectedDimensions() {
		Room room = new RoomTemplate(new String[]{"##.", ".P."}).createRoom();
		assertEquals(3, room.getWidth());
		assertEquals(2, room.getHeight());
	}

	@Test
	void createRoomMapsTilesBottomUp() {
		Room room = new RoomTemplate(new String[]{"#.", ".#"}).createRoom();
		assertEquals(TileType.FLOOR, room.getTileAt(0, 0));
		assertEquals(TileType.WALL,  room.getTileAt(1, 0));
		assertEquals(TileType.WALL,  room.getTileAt(0, 1));
		assertEquals(TileType.FLOOR, room.getTileAt(1, 1));
	}

	@Test
	void createRoomHashCharBecomesWall() {
		Room room = new RoomTemplate(new String[]{"#"}).createRoom();
		assertEquals(TileType.WALL, room.getTileAt(0, 0));
	}

	@Test
	void createRoomSpaceCharBecomesEmpty() {
		Room room = new RoomTemplate(new String[]{" "}).createRoom();
		assertEquals(TileType.EMPTY, room.getTileAt(0, 0));
	}

	@Test
	void createRoomDoorCharBecomesDoorClosed() {
		Room room = new RoomTemplate(new String[]{"D"}).createRoom();
		assertEquals(TileType.DOOR_CLOSED, room.getTileAt(0, 0));
	}

	@Test
	void createRoomLeftWallDoorUsesLeftWallDoorTile() {
		Room room = new RoomTemplate(new String[]{"...", "D..", "..."}).createRoom();
		assertEquals(TileType.DOOR_LEFT_WALL_CLOSED, room.getTileAt(0, 1));
	}

	@Test
	void createRoomRightWallDoorUsesRightWallDoorTile() {
		Room room = new RoomTemplate(new String[]{"...", "..D", "..."}).createRoom();
		assertEquals(TileType.DOOR_RIGHT_WALL_CLOSED, room.getTileAt(2, 1));
	}

	@Test
	void createRoomTopWallDoorUsesTopDoorTile() {
		Room room = new RoomTemplate(new String[]{".D.", "...", "..."}).createRoom();
		assertEquals(TileType.DOOR_TOP_CLOSED, room.getTileAt(1, 2));
	}

	@Test
	void createRoomBottomWallDoorUsesBottomDoorTile() {
		Room room = new RoomTemplate(new String[]{"...", "...", ".D."}).createRoom();
		assertEquals(TileType.DOOR_BOTTOM_CLOSED, room.getTileAt(1, 0));
	}

	@Test
	void createRoomUnknownCharBecomesFloor() {
		Room room = new RoomTemplate(new String[]{"X"}).createRoom();
		assertEquals(TileType.FLOOR, room.getTileAt(0, 0));
	}

	@Test
	void createRoomDoorCharAddsSpawnPointOfTypeDoor() {
		Room room = new RoomTemplate(new String[]{"D"}).createRoom();
		List<SpawnPoint> spawnPoints = room.getSpawnPoints();
		assertEquals(1, spawnPoints.size());
		assertEquals(SpawnPoint.Type.DOOR, spawnPoints.getFirst().getType());
		assertEquals(0, spawnPoints.getFirst().getX());
		assertEquals(0, spawnPoints.getFirst().getY());
	}

	@Test
	void createRoomWallAndFloorDoNotAddSpawnPoints() {
		Room room = new RoomTemplate(new String[]{".#"}).createRoom();
		assertTrue(room.getSpawnPoints().isEmpty());
	}
}