package inf112.dungeoncrawler.controller;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.generation.SpawnPoint;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.sound.SoundService;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameScreenTest {

	private static final SoundService NO_SOUND = effect -> {};

	GameScreen screen;
	Room room;

	private void setField(GameScreen screen) throws Exception {
		Field f = GameScreen.class.getDeclaredField("isPaused");
		f.setAccessible(true);
		f.set(screen, true);
	}

	@BeforeEach
	void setUp() {
		screen = new GameScreen(null);
		room = new Room(20, 20);
	}

	@Test
	void notPausedWhenCreated() {
		assertFalse(screen.isPaused());
	}

	@Test
	void setPausedIsTrue() throws Exception {
		setField(screen);
		assertTrue(screen.isPaused());
	}

	@Test
	void createsPlayerAtSpawnPoint() {
		room.addSpawnPoint(new SpawnPoint(100, 200, SpawnPoint.Type.PLAYER));

		GameWorld world = screen.buildGameWorld(room, NO_SOUND);

		assertNotNull(world.getPlayer());
		assertEquals(100, world.getPlayer().getX(), 0.01f);
		assertEquals(200, world.getPlayer().getY(), 0.01f);
	}

	@Test
	void spawnsEnemiesFromSpawnPoints() {
		Room room = new Room(20, 20);
		room.addSpawnPoint(new SpawnPoint(300, 300, SpawnPoint.Type.NORMALENEMY));
		room.addSpawnPoint(new SpawnPoint(400, 400, SpawnPoint.Type.FASTENEMY));

		GameWorld world = screen.buildGameWorld(room, NO_SOUND);
		List<Entity> entities = world.getEntities();
		// removes player which is always in world.getEntities()
		entities.removeFirst();

		assertEquals(2, world.getEntities().size());
	}

	@Test
	void spawnsPowerupsFromSpawnPoints() {
		Room room = new Room(20, 20);
		room.addSpawnPoint(new SpawnPoint(100, 200, SpawnPoint.Type.HEALTHPOWERUP));
		room.addSpawnPoint(new SpawnPoint(300, 300, SpawnPoint.Type.ATTACKPOWERUP));
		room.addSpawnPoint(new SpawnPoint(400, 400, SpawnPoint.Type.SPEEDPOWERUP));

		GameWorld world = screen.buildGameWorld(room, NO_SOUND);
		List<Entity> entities = world.getEntities();
		// removes player which is always in world.getEntities()
		entities.removeFirst();

		assertEquals(3, world.getEntities().size());
	}
}