package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.world.GameWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    private Entity entity;

    @BeforeEach
    void setUp() {
        entity = makeEntity();
        entity.width  = 32;
        entity.height = 48;
        entity.health = 10;
    }

    @Test
    void getXReturnsConstructorValue() {
        assertEquals(100, entity.getX());
    }

    @Test
    void getYReturnsConstructorValue() {
        assertEquals(100, entity.getY());
    }

    @Test
    void getWidthReturnsAssignedValue() {
        assertEquals(32, entity.getWidth());
    }

    @Test
    void getHeightReturnsAssignedValue() {
        assertEquals(48, entity.getHeight());
    }

    @Test
    void getHealthReturnsAssignedValue() {
        assertEquals(10, entity.getHealth());
    }

    @Test
    void setPositionUpdatesCoordinates() {
        entity.setPosition(99, 88);
        assertEquals(99, entity.getX());
        assertEquals(88, entity.getY());
    }

    @Test
    void takeDamageReducesHealth() {
        entity.takeDamage(3, 0, 0);
        assertEquals(7, entity.getHealth());
    }

    @Test
    void takeDamageLethalDamageSetsIsAliveFalse() {
        entity.takeDamage(10, 0, 0);
        assertFalse(entity.isAlive());
    }

    @Test
    void takeDamageNonLethalRemainsAlive() {
        entity.takeDamage(5, 0, 0);
        assertTrue(entity.isAlive());
    }

    @Test
    void isAliveInitiallyTrue() {
        assertTrue(entity.isAlive());
    }

    @Test
    void applyKnockbackStoresKnockbackValues() {
        entity.knockBackForce = 100;
        entity.applyKnockback(50, 75);
        assertEquals(50, entity.knockbackX, 0.001f);
        assertEquals(75, entity.knockbackY, 0.001f);
    }

    private Entity makeEntity() {
        return new Entity((float) 100, (float) 100) {
            @Override public void step(GameWorld world, double dt) {}
        };
    }
}