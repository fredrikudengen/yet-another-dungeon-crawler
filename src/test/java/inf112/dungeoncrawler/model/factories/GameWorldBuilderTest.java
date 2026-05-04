package inf112.dungeoncrawler.model.factories;

import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.enemies.Enemy;
import inf112.dungeoncrawler.model.generation.SpawnPoint;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;
import static org.junit.jupiter.api.Assertions.*;

class GameWorldBuilderTest {

    private static final SoundService NO_SOUND = new SoundService() {
        @Override
        public void play(SoundEffect effect) { /* test */ }

        @Override
        public void dispose() { /* test */ }
    };

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room(20, 20);
    }

    @Test
    void constructorNullRoomThrowsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new GameWorldBuilder(null, NO_SOUND));
    }

    @Test
    void constructorNullSoundThrowsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new GameWorldBuilder(room, null));
    }

    @Test
    void buildNoPlayerSpawnPointThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class,
                () -> new GameWorldBuilder(room, NO_SOUND).build());
    }

    @Test
    void buildWithPlayerSpawnPointReturnsWorldWithPlayer() {
        room.addSpawnPoint(new SpawnPoint(TILE_SIZE, TILE_SIZE, SpawnPoint.Type.PLAYER));

        GameWorld world = new GameWorldBuilder(room, NO_SOUND).build();

        assertNotNull(world.getPlayer());
        assertInstanceOf(Player.class, world.getPlayer());
    }

    @Test
    void buildPlayerSpawnedAtCorrectPosition() {
        room.addSpawnPoint(new SpawnPoint(3 * TILE_SIZE, 4 * TILE_SIZE, SpawnPoint.Type.PLAYER));

        GameWorld world = new GameWorldBuilder(room, NO_SOUND).build();

        assertEquals(3 * TILE_SIZE, world.getPlayer().getX());
        assertEquals(4 * TILE_SIZE, world.getPlayer().getY());
    }

    @Test
    void buildWithEnemySpawnPointsSpawnsEnemiesInWorld() {
        room.addSpawnPoint(new SpawnPoint(TILE_SIZE,     TILE_SIZE,     SpawnPoint.Type.PLAYER));
        room.addSpawnPoint(new SpawnPoint(3 * TILE_SIZE, TILE_SIZE,     SpawnPoint.Type.FASTENEMY));
        room.addSpawnPoint(new SpawnPoint(5 * TILE_SIZE, TILE_SIZE,     SpawnPoint.Type.NORMALENEMY));

        GameWorld world = new GameWorldBuilder(room, NO_SOUND).build();

        // this code-section was adapted using Claude Sonnet 4.6
        long enemyCount = world.getEntities().stream()
                .filter(e -> e instanceof Enemy)
                .count();
        assertEquals(2, enemyCount);
    }

    @Test
    void buildWithNoEnemySpawnPointsWorldHasNoEnemies() {
        room.addSpawnPoint(new SpawnPoint(TILE_SIZE, TILE_SIZE, SpawnPoint.Type.PLAYER));

        GameWorld world = new GameWorldBuilder(room, NO_SOUND).build();

        assertFalse(world.hasEnemiesRemaining());
    }

    @Test
    void buildPlayerIncludedInEntities() {
        room.addSpawnPoint(new SpawnPoint(TILE_SIZE, TILE_SIZE, SpawnPoint.Type.PLAYER));

        GameWorld world = new GameWorldBuilder(room, NO_SOUND).build();

        assertTrue(world.getEntities().contains(world.getPlayer()));
    }

    @Test
    void buildWithDoorSpawnPointDoesNotThrowOrSpawnEntity() {
        room.addSpawnPoint(new SpawnPoint(TILE_SIZE, TILE_SIZE, SpawnPoint.Type.PLAYER));
        room.addSpawnPoint(new SpawnPoint(5 * TILE_SIZE, 5 * TILE_SIZE, SpawnPoint.Type.DOOR));

        GameWorld world = assertDoesNotThrow(() -> new GameWorldBuilder(room, NO_SOUND).build());

        // this code-section was adapted using Claude Sonnet 4.6
        long nonPlayerEntities = world.getEntities().stream()
                .filter(entity -> entity != world.getPlayer())
                .count();
        assertEquals(0, nonPlayerEntities);
    }

    @Test
    void buildWithPlayerReusesPlayerAndPreservesHealth() {
        room.addSpawnPoint(new SpawnPoint(10 * TILE_SIZE, 10 * TILE_SIZE, SpawnPoint.Type.FASTENEMY));

        Player persistentPlayer = new Player(0, 0, NO_SOUND);
        persistentPlayer.setHealth(2);

        GameWorld world = new GameWorldBuilder(room, NO_SOUND)
                .buildWithPlayer(persistentPlayer, 3 * TILE_SIZE, 4 * TILE_SIZE);

        assertSame(persistentPlayer, world.getPlayer());
        assertEquals(2, world.getPlayer().getHealth());
        assertEquals(3 * TILE_SIZE, world.getPlayer().getX());
        assertEquals(4 * TILE_SIZE, world.getPlayer().getY());
    }
}