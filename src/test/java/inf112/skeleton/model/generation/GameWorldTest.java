package inf112.skeleton.model.generation;

import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.entities.Player;
import inf112.skeleton.model.entities.powerups.Powerup;
import inf112.skeleton.model.world.GameWorld;
import inf112.skeleton.model.world.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameWorldTest {

    private Room room;
    private GameWorld world;
    private Player player;

    @BeforeEach
    void setUp() {
        room = new Room(20, 20);
        world = new GameWorld(room);
        player = new Player(300, 300);
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
        assertTrue(world.getPowerups().isEmpty());
    }

    @Test
    void testAddEntityAppearsInList() {
        Entity entity = makeEntity(100, 100);
        world.addEntity(entity);
        assertTrue(world.getEntities().contains(entity));
    }

    @Test
    void testAddMultipleEntitiesAllStored() {
        world.addEntity(makeEntity(100, 100));
        world.addEntity(makeEntity(200, 200));
        assertEquals(2, world.getEntities().size());
    }

    @Test
    void testAddPowerupAppearsInList() {
        Powerup powerup = makePowerup(100, 100);
        world.addPowerup(powerup);
        assertTrue(world.getPowerups().contains(powerup));
    }

    @Test
    void testStepRemovesDeadEntity() {
        Entity dead = makeEntity(100, 100);
        world.addEntity(dead);
        dead.health = 0;
        world.step(0.016, player);
        assertFalse(world.getEntities().contains(dead));
    }

    @Test
    void testStepKeepsAliveEntity() {
        Entity alive = makeEntity(100, 100);
        world.addEntity(alive);
        world.step(0.016, player);
        assertTrue(world.getEntities().contains(alive));
    }

    @Test
    void testStepOnlyRemovesDeadEntities() {
        Entity alive = makeEntity(100, 100);
        Entity dead  = makeEntity(200, 200);
        dead.health = 0;
        world.addEntity(alive);
        world.addEntity(dead);
        world.step(0.016, player);

        List<Entity> remaining = world.getEntities();
        assertTrue(remaining.contains(alive));
        assertFalse(remaining.contains(dead));
        assertEquals(1, remaining.size());
    }

    @Test
    void testStepRemovesPowerupCollidingWithPlayer() {
        Powerup powerup = makePowerup(player.getX(), player.getY());
        world.addPowerup(powerup);
        world.step(0.016, player);
        assertFalse(world.getPowerups().contains(powerup));
    }

    @Test
    void testStepKeepsPowerupNotCollidingWithPlayer() {
        Powerup powerup = makePowerup(0, 0);
        world.addPowerup(powerup);
        world.step(0.016, player);
        assertTrue(world.getPowerups().contains(powerup));
    }

    @Test
    void testStepOnlyRemovesCollidingPowerup() {
        Powerup colliding    = makePowerup(player.getX(), player.getY());
        Powerup notColliding = makePowerup(0, 0);
        world.addPowerup(colliding);
        world.addPowerup(notColliding);
        world.step(0.016, player);

        List<Powerup> remaining = world.getPowerups();
        assertFalse(remaining.contains(colliding));
        assertTrue(remaining.contains(notColliding));
        assertEquals(1, remaining.size());
    }

    @Test
    void testIteratorReturnsAllEntities() {
        Entity e1 = makeEntity(100, 100);
        Entity e2 = makeEntity(200, 200);
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

    private Entity makeEntity(float x, float y) {
        return new Entity(x, y) {
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
            public String getTextureName() { return "test"; }
        };
    }

    private Powerup makePowerup(float x, float y) {
        return new Powerup(x, y) {
            @Override
            public float getX() {
                return this.x;
            }

            @Override
            public float getY() {
                return this.y;
            }

            @Override
            public float getWidth() {
                return this.width;
            }

            @Override
            public float getHeight() {
                return this.height;
            }

            {
                this.width = 16;
                this.height = 16;
            }

            @Override
            public void step(Player player, double dt) {}

            @Override
            public String getTextureName() { return "test_powerup"; }
        };
    }
}