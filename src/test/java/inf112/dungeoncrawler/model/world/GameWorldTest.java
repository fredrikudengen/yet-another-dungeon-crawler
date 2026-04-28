package inf112.dungeoncrawler.model.world;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.enemies.FastEnemy;
import inf112.dungeoncrawler.model.entities.powerups.HealthPowerup;
import inf112.dungeoncrawler.model.entities.powerups.Powerup;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameWorldTest {

    private static final SoundService NO_SOUND = new SoundService() {
        @Override
        public void play(SoundEffect effect) { /* ingenting */ }

        @Override
        public void dispose() { /* ingenting */ }
    };

    private Room room;
    private GameWorld world;
    private Player player;

    @BeforeEach
    void setUp() {
        room   = new Room(20, 20);
        player = new Player(300, 300, NO_SOUND);
        world  = new GameWorld(room, player);
    }

    @Test
    void getRoom_returnsConstructorRoom() {
        assertSame(room, world.getRoom());
    }

    @Test
    void getPlayer_returnsConstructorPlayer() {
        assertSame(player, world.getPlayer());
    }

    @Test
    void newWorld_hasNoEntities() {
        assertTrue(world.getEntities().isEmpty());
    }

    @Test
    void addEntity_appearsInList() {
        Entity enemy = makeFastEnemy(100, 100);
        world.addEntity(enemy);
        assertTrue(world.getEntities().contains(enemy));
    }

    @Test
    void addMultipleEntities_allStored() {
        world.addEntity(makeFastEnemy(100, 100));
        world.addEntity(makeFastEnemy(200, 200));
        assertEquals(2, world.getEntities().size());
    }

    @Test
    void step_removesDeadEntity() {
        Entity dead = makeDeadEnemy(100, 100);
        world.addEntity(dead);
        world.step(0.016);
        assertFalse(world.getEntities().contains(dead));
    }

    @Test
    void step_keepsAliveEntity() {
        Entity alive = makeFastEnemy(100, 100);
        world.addEntity(alive);
        world.step(0.016);
        assertTrue(world.getEntities().contains(alive));
    }

    @Test
    void step_onlyRemovesDeadEntities() {
        Entity alive = makeFastEnemy(100, 100);
        Entity dead  = makeDeadEnemy(200, 200);
        world.addEntity(alive);
        world.addEntity(dead);
        world.step(0.016);

        List<Entity> remaining = world.getEntities();
        assertTrue(remaining.contains(alive));
        assertFalse(remaining.contains(dead));
        assertEquals(1, remaining.size());
    }

    @Test
    void step_removesPowerupCollidingWithPlayer() {
        HealthPowerup powerup = makePowerup(player.getX(), player.getY());
        world.addEntity(powerup);
        world.step(1);
        assertFalse(world.getEntities().contains(powerup));
    }

    @Test
    void step_keepsPowerupNotCollidingWithPlayer() {
        Powerup powerup = makePowerup(0, 0);
        world.addEntity(powerup);
        world.step(0.016);
        assertTrue(world.getEntities().contains(powerup));
    }

    @Test
    void hasEnemiesRemaining_noEnemies_returnsFalse() {
        assertFalse(world.hasEnemiesRemaining());
    }

    @Test
    void hasEnemiesRemaining_withAliveEnemy_returnsTrue() {
        world.addEntity(makeFastEnemy(100, 100));
        assertTrue(world.hasEnemiesRemaining());
    }

    @Test
    void hasEnemiesRemaining_afterEnemyDies_returnsFalse() {
        world.addEntity(makeDeadEnemy(100, 100));
        world.step(0.016);
        assertFalse(world.hasEnemiesRemaining());
    }

    @Test
    void getEnemiesRemainingCount_initiallyZero() {
        assertEquals(0, world.getEnemiesRemainingCount());
    }

    @Test
    void getEnemiesRemainingCount_countsOnlyAliveEnemies() {
        world.addEntity(makeFastEnemy(100, 100));
        world.addEntity(makeDeadEnemy(200, 200));
        world.step(0.016);

        assertEquals(1, world.getEnemiesRemainingCount());
    }

    @Test
    void getKilledEnemies_initiallyZero() {
        assertEquals(0, world.getKilledEnemies());
    }

    @Test
    void getKilledEnemies_incrementsWhenEnemyRemoved() {
        world.addEntity(makeDeadEnemy(100, 100));
        world.step(0.016);
        assertEquals(1, world.getKilledEnemies());
    }

    @Test
    void getKilledEnemies_doesNotCountPowerups() {
        HealthPowerup powerup = makePowerup(player.getX(), player.getY());
        world.addEntity(powerup);
        world.step(1);
        assertEquals(0, world.getKilledEnemies());
    }

    @Test
    void iterator_containsAllEntities() {
        Entity e1 = makeFastEnemy(100, 100);
        Entity e2 = makeFastEnemy(200, 200);
        world.addEntity(e1);
        world.addEntity(e2);

        List<Entity> iterated = new java.util.ArrayList<>();
        world.iterator().forEachRemaining(iterated::add);

        assertTrue(iterated.contains(e1));
        assertTrue(iterated.contains(e2));
        assertEquals(2, iterated.size());
    }

    @Test
    void iterator_emptyWorld_hasNoElements() {
        assertFalse(world.iterator().hasNext());
    }

    private FastEnemy makeFastEnemy(float x, float y) {
        return new FastEnemy(x, y, player) {
            { width = 32; height = 32; health = 10; speed = 100; damage = 5; }
            @Override public void step(GameWorld world, double dt) {}
            @Override public String getBaseTexturePath() { return ""; }
        };
    }

    private FastEnemy makeDeadEnemy(float x, float y) {
        return new FastEnemy(x, y, player) {
            { isAlive = false; }
            @Override public void step(GameWorld world, double dt) {}
            @Override public String getBaseTexturePath() { return ""; }
        };
    }

    private HealthPowerup makePowerup(float x, float y) {
        return new HealthPowerup(x, y, NO_SOUND) {
            @Override public String getBaseTexturePath() { return "test"; }
        };
    }
}