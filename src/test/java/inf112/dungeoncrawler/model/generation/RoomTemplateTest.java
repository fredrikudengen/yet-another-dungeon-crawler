package inf112.dungeoncrawler.model.generation;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.model.world.TileType;

class RoomTemplateTest {

	@Test
	void testCreateRoomHasExpectedDimensions() {
		RoomTemplate template = new RoomTemplate(new String[] { "##.", ".P." });

		Room room = template.createRoom();

		assertEquals(3, room.getWidth());
		assertEquals(2, room.getHeight());
	}

	@Test
	void testCreateRoomMapsTilesBottomUp() {
		RoomTemplate template = new RoomTemplate(new String[] { "#.", ".#" });

		Room room = template.createRoom();

		assertEquals(TileType.FLOOR, room.getTileAt(0, 0));
		assertEquals(TileType.WALL, room.getTileAt(1, 0));
		assertEquals(TileType.WALL, room.getTileAt(0, 1));
		assertEquals(TileType.FLOOR, room.getTileAt(1, 1));
	}

	@Test
	void testCreateRoomTreatsNonWallCharactersAsFloor() {
		RoomTemplate template = new RoomTemplate(new String[] { "X" });

		Room room = template.createRoom();

		assertEquals(TileType.FLOOR, room.getTileAt(0, 0));
	}

	@Test
	void testCreateRoomAddsExpectedSpawnPointTypesAndCoordinates() {
		RoomTemplate template = new RoomTemplate(new String[] { "PFNLSHA" });

		Room room = template.createRoom();
		List<SpawnPoint> spawnPoints = room.getSpawnPoints();

		assertEquals(7, spawnPoints.size());

		assertSpawn(spawnPoints.get(0), 0, 0, SpawnPoint.Type.PLAYER);
		assertSpawn(spawnPoints.get(1), TILE_SIZE, 0, SpawnPoint.Type.FASTENEMY);
		assertSpawn(spawnPoints.get(2), TILE_SIZE*2, 0, SpawnPoint.Type.NORMALENEMY);
		assertSpawn(spawnPoints.get(3), TILE_SIZE*3, 0, SpawnPoint.Type.SLOWENEMY);
		assertSpawn(spawnPoints.get(4), TILE_SIZE*4, 0, SpawnPoint.Type.SPEEDPOWERUP);
		assertSpawn(spawnPoints.get(5), TILE_SIZE*5, 0, SpawnPoint.Type.HEALTHPOWERUP);
		assertSpawn(spawnPoints.get(6), TILE_SIZE*6, 0, SpawnPoint.Type.ATTACKPOWERUP);
	}

	@Test
	void testCreateRoomDoesNotAddSpawnPointForRegularFloorOrWall() {
		RoomTemplate template = new RoomTemplate(new String[] { ".#" });

		Room room = template.createRoom();

		assertTrue(room.getSpawnPoints().isEmpty());
	}

	private void assertSpawn(SpawnPoint spawnPoint, int expectedX, int expectedY, SpawnPoint.Type expectedType) {
		assertEquals(expectedX, spawnPoint.x);
		assertEquals(expectedY, spawnPoint.y);
		assertEquals(expectedType, spawnPoint.type);
	}
}
