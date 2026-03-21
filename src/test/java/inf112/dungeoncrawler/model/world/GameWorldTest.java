package inf112.dungeoncrawler.model.world;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.enemies.FastEnemy;
import inf112.dungeoncrawler.model.entities.powerups.HealthPowerup;
import inf112.dungeoncrawler.model.entities.powerups.Powerup;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameWorldTest {

    private Room room;
    private GameWorld world;
    private Player player;
    private static final SoundService NO_SOUND = effect -> {};

    @BeforeEach
    void setUp() {
        room   = new Room(20, 20);
        player = new Player(300, 300, NO_SOUND);
        world  = new GameWorld(room, player);
    }

    @Test
    void testGetRoomReturnsConstructorRoom() {
        assertSame(room, world.getRoom());
    }

    @Test
    void testNewWorldHasNoEntities() {
        assertTrue(world.getEntities().isEmpty());
    }

    @Test
    void testNewWorldHasNoPowerups() {
        assertTrue(world.getEntities().isEmpty());
    }

    @Test
    void testAddEntityAppearsInList() {
        Entity entity = makeFastEnemy(100, 100);
        world.addEntity(entity);
        assertTrue(world.getEntities().contains(entity));
    }

    @Test
    void testAddMultipleEntitiesAllStored() {
        world.addEntity(makeFastEnemy(100, 100));
        world.addEntity(makeFastEnemy(200, 200));
        assertEquals(2, world.getEntities().size());
    }

    @Test
    void testAddPowerupAppearsInList() {
        Powerup powerup = makePowerup(100, 100);
        world.addEntity(powerup);
        assertTrue(world.getEntities().contains(powerup));
    }

    @Test
    void testStepRemovesDeadEntity() {
        Entity dead = makeDeadEnemy(100, 100);
        world.addEntity(dead);
        world.step(0.016);
        assertFalse(world.getEntities().contains(dead));
    }

    @Test
    void testStepKeepsAliveEntity() {
        Entity alive = makeFastEnemy(100, 100);
        world.addEntity(alive);
        world.step(0.016);
        assertTrue(world.getEntities().contains(alive));
    }

    @Test
    void testStepOnlyRemovesDeadEntities() {
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
    void testStepRemovesPowerupCollidingWithPlayer() {
        HealthPowerup powerup = makePowerup(player.getX(), player.getY());
        world.addEntity(powerup);
        world.step(1);
        assertFalse(world.getEntities().contains(powerup));
    }

    @Test
    void testStepKeepsPowerupNotCollidingWithPlayer() {
        Powerup powerup = makePowerup(0, 0);
        world.addEntity(powerup);
        world.step(0.016);
        assertTrue(world.getEntities().contains(powerup));
    }

    @Test
    void testStepOnlyRemovesCollidingPowerup() {
        HealthPowerup colliding    = makePowerup(player.getX(), player.getY());
        HealthPowerup notColliding = makePowerup(0, 0);
        world.addEntity(colliding);
        world.addEntity(notColliding);
        world.step(1);

        List<Entity> remaining = world.getEntities();
        assertFalse(remaining.contains(colliding));
        assertTrue(remaining.contains(notColliding));
        assertEquals(1, remaining.size());
    }

    @Test
    void testIteratorReturnsAllEntities() {
        Entity e1 = makeFastEnemy(100, 100);
        Entity e2 = makeFastEnemy(200, 200);
        world.addEntity(e1);
        world.addEntity(e2);

        Iterator<Entity> it = world.iterator();
        assertSame(e1, it.next());
        assertSame(e2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testIteratorEmptyWorldHasNoElements() {
        assertFalse(world.iterator().hasNext());
    }

    private FastEnemy makeFastEnemy(float x, float y) {
        return new FastEnemy(x, y, player) {
            {
                this.width = 32;
                this.height = 32;
                this.health = 10;
                this.speed = 100;
                this.damage = 5;
            }

            @Override
            public void step(GameWorld world, double dt) {}

            @Override
            public String getBaseTexturePath() {
                return "";
            }
        };
    }

    private FastEnemy makeDeadEnemy(float x, float y) {
        return new FastEnemy(x, y, player) {
            {
                this.isAlive = false;
            }

            @Override
            public void step(GameWorld world, double dt) {}

            @Override
            public String getBaseTexturePath() {
                return "";
            }
        };
    }

    private HealthPowerup makePowerup(float x, float y) {
        return new HealthPowerup(x, y, NO_SOUND) {

            @Override
            public String getBaseTexturePath() {
                return "test";
            }
        };
    }
}