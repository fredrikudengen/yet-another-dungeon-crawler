package inf112.skeleton.model.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import inf112.skeleton.model.world.GameWorld;

class EntityTest {

    private Entity makeEntity(float x, float y, float w, float h, int health) {
        return new Entity(x, y, w, h, 100, health) {
            @Override public void step(GameWorld world, double dt) {}
            @Override public String getTextureName() { return "test"; }
        };
    }

    @Test
    void testGetXReturnsConstructorValue() {
        Entity e = makeEntity(42, 0, 10, 10, 5);
        assertEquals(42f, e.getX());
    }

    @Test
    void testGetYReturnsConstructorValue() {
        Entity e = makeEntity(0, 77, 10, 10, 5);
        assertEquals(77f, e.getY());
    }

    @Test
    void testGetWidthAndHeight() {
        Entity e = makeEntity(0, 0, 32, 48, 5);
        assertEquals(32f, e.getWidth());
        assertEquals(48f, e.getHeight());
    }

    @Test
    void testGetHealthReturnsInitialHealth() {
        Entity e = makeEntity(0, 0, 10, 10, 10);
        assertEquals(10, e.getHealth());
    }

    @Test
    void testTakeDamageReducesHealth() {
        Entity e = makeEntity(0, 0, 10, 10, 10);
        e.takeDamage(3);
        assertEquals(7, e.getHealth());
    }

    @Test
    void testSetPositionUpdatesXAndY() {
        Entity e = makeEntity(0, 0, 10, 10, 5);
        e.setPosition(99, 88);
        assertEquals(99f, e.getX());
        assertEquals(88f, e.getY());
    }
}
