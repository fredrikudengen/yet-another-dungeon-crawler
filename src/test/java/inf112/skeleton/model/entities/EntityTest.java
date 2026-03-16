package inf112.skeleton.model.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inf112.skeleton.model.world.GameWorld;

class EntityTest {

    private Entity e;

    private Entity makeEntity(float x, float y) {
        return new Entity(x, y) {

            @Override
            public void step(GameWorld world, double dt) {}
        };
    }

    @BeforeEach
    void setUp() {
        e = makeEntity(100,100);
    }

    @Test
    void testGetXReturnsConstructorValue() {
        assertEquals(100, e.getX());
    }

    @Test
    void testGetYReturnsConstructorValue() {
        assertEquals(100, e.getY());
    }

    @Test
    void testGetWidthAndHeight() {
        e.width = 32;
        e.height = 48;
        
        assertEquals(32, e.getWidth());
        assertEquals(48, e.getHeight());
    }

    @Test
    void testGetHealthReturnsInitialHealth() {
        e.health = 10;
        assertEquals(10, e.getHealth());
    }

    @Test
    void testTakeDamageReducesHealth() {
        e.health = 10;
        e.takeDamage(3);
        assertEquals(7, e.getHealth());
    }

    @Test
    void testSetPositionUpdatesXAndY() {
        e.setPosition(99, 88);
        assertEquals(99, e.getX());
        assertEquals(88, e.getY());
    }
}