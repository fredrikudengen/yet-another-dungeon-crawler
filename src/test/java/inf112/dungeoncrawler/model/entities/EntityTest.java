package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.world.GameWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    private Entity entity;

    @BeforeEach
    void setUp() {
        entity = makeEntity(100, 100);
        entity.width  = 32;
        entity.height = 48;
        entity.health = 10;
    }

    @Test
    void getX_returnsConstructorValue() {
        assertEquals(100, entity.getX());
    }

    @Test
    void getY_returnsConstructorValue() {
        assertEquals(100, entity.getY());
    }

    @Test
    void getWidth_returnsAssignedValue() {
        assertEquals(32, entity.getWidth());
    }

    @Test
    void getHeight_returnsAssignedValue() {
        assertEquals(48, entity.getHeight());
    }

    @Test
    void getHealth_returnsAssignedValue() {
        assertEquals(10, entity.getHealth());
    }

    @Test
    void setPosition_updatesCoordinates() {
        entity.setPosition(99, 88);
        assertEquals(99, entity.getX());
        assertEquals(88, entity.getY());
    }

    @Test
    void takeDamage_reducesHealth() {
        entity.takeDamage(3, 0, 0);
        assertEquals(7, entity.getHealth());
    }

    @Test
    void takeDamage_lethalDamage_setsIsAliveFalse() {
        entity.takeDamage(10, 0, 0);
        assertFalse(entity.isAlive());
    }

    @Test
    void takeDamage_nonLethal_remainsAlive() {
        entity.takeDamage(5, 0, 0);
        assertTrue(entity.isAlive());
    }

    @Test
    void isAlive_initiallyTrue() {
        assertTrue(entity.isAlive());
    }

    @Test
    void applyKnockback_storesKnockbackValues() {
        entity.knockBackForce = 100;
        entity.applyKnockback(50, 75);
        assertEquals(50, entity.knockbackX, 0.001f);
        assertEquals(75, entity.knockbackY, 0.001f);
    }

    private Entity makeEntity(float x, float y) {
        return new Entity(x, y) {
            @Override public void step(GameWorld world, double dt) {}
            @Override public String getBaseTexturePath() { return "test"; }
        };
    }
}