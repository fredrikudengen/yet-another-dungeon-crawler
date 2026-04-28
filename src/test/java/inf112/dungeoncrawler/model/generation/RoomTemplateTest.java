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
	void createRoom_mapsTilesBottomUp() {
		Room room = new RoomTemplate(new String[]{"#.", ".#"}).createRoom();
		assertEquals(TileType.FLOOR, room.getTileAt(0, 0));
		assertEquals(TileType.WALL,  room.getTileAt(1, 0));
		assertEquals(TileType.WALL,  room.getTileAt(0, 1));
		assertEquals(TileType.FLOOR, room.getTileAt(1, 1));
	}

	@Test
	void createRoom_hashCharBecomesWall() {
		Room room = new RoomTemplate(new String[]{"#"}).createRoom();
		assertEquals(TileType.WALL, room.getTileAt(0, 0));
	}

	@Test
	void createRoom_spaceCharBecomesEmpty() {
		Room room = new RoomTemplate(new String[]{" "}).createRoom();
		assertEquals(TileType.EMPTY, room.getTileAt(0, 0));
	}

	@Test
	void createRoom_doorCharBecomesDoorClosed() {
		Room room = new RoomTemplate(new String[]{"D"}).createRoom();
		assertEquals(TileType.DOOR_CLOSED, room.getTileAt(0, 0));
	}

	@Test
	void createRoom_leftWallDoor_usesLeftWallDoorTile() {
		Room room = new RoomTemplate(new String[]{"...", "D..", "..."}).createRoom();
		assertEquals(TileType.DOOR_LEFT_WALL_CLOSED, room.getTileAt(0, 1));
	}

	@Test
	void createRoom_rightWallDoor_usesRightWallDoorTile() {
		Room room = new RoomTemplate(new String[]{"...", "..D", "..."}).createRoom();
		assertEquals(TileType.DOOR_RIGHT_WALL_CLOSED, room.getTileAt(2, 1));
	}

	@Test
	void createRoom_topWallDoor_usesTopDoorTile() {
		Room room = new RoomTemplate(new String[]{".D.", "...", "..."}).createRoom();
		assertEquals(TileType.DOOR_TOP_CLOSED, room.getTileAt(1, 2));
	}

	@Test
	void createRoom_bottomWallDoor_usesBottomDoorTile() {
		Room room = new RoomTemplate(new String[]{"...", "...", ".D."}).createRoom();
		assertEquals(TileType.DOOR_BOTTOM_CLOSED, room.getTileAt(1, 0));
	}

	@Test
	void createRoom_unknownCharBecomesFloor() {
		Room room = new RoomTemplate(new String[]{"X"}).createRoom();
		assertEquals(TileType.FLOOR, room.getTileAt(0, 0));
	}

	@Test
	void createRoom_doorCharAddsSpawnPointOfTypeDoor() {
		Room room = new RoomTemplate(new String[]{"D"}).createRoom();
		List<SpawnPoint> spawnPoints = room.getSpawnPoints();
		assertEquals(1, spawnPoints.size());
		assertEquals(SpawnPoint.Type.DOOR, spawnPoints.getFirst().getType());
		assertEquals(0, spawnPoints.getFirst().getX());
		assertEquals(0, spawnPoints.getFirst().getY());
	}

	@Test
	void createRoom_wallAndFloor_doNotAddSpawnPoints() {
		Room room = new RoomTemplate(new String[]{".#"}).createRoom();
		assertTrue(room.getSpawnPoints().isEmpty());
	}
}