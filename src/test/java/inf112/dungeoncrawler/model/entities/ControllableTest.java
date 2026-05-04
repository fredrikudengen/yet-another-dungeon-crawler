package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllableTest {

    private static class ControllableStub implements Controllable {
        boolean movingLeft, movingRight, movingUp, movingDown;
        Direction lastAttackDirection;
        int attackCallCount;

        @Override public void setMovingLeft(boolean v)  { movingLeft  = v; }
        @Override public void setMovingRight(boolean v) { movingRight = v; }
        @Override public void setMovingUp(boolean v)    { movingUp    = v; }
        @Override public void setMovingDown(boolean v)  { movingDown  = v; }
        @Override public void performAttack(Direction d) {
            lastAttackDirection = d;
            attackCallCount++;
        }
    }

    private ControllableStub controllable;

    @BeforeEach
    void setUp() {
        controllable = new ControllableStub();
    }

    @Test
    void setMovingLeftPropagatesValue() {
        controllable.setMovingLeft(true);
        assertTrue(controllable.movingLeft);

        controllable.setMovingLeft(false);
        assertFalse(controllable.movingLeft);
    }

    @Test
    void setMovingRightPropagatesValue() {
        controllable.setMovingRight(true);
        assertTrue(controllable.movingRight);
    }

    @Test
    void setMovingUpPropagatesValue() {
        controllable.setMovingUp(true);
        assertTrue(controllable.movingUp);
    }

    @Test
    void setMovingDownPropagatesValue() {
        controllable.setMovingDown(true);
        assertTrue(controllable.movingDown);
    }

    @Test
    void performAttackPassesDirection() {
        controllable.performAttack(Direction.UP);
        assertEquals(Direction.UP, controllable.lastAttackDirection);
    }

    @Test
    void controllableCanBeUsedPolymorphically() {
        Controllable c = controllable;
        c.setMovingLeft(true);
        c.performAttack(Direction.RIGHT);

        assertTrue(controllable.movingLeft);
        assertEquals(Direction.RIGHT, controllable.lastAttackDirection);
        assertEquals(1, controllable.attackCallCount);
    }
}