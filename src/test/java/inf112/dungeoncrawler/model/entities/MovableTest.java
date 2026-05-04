package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovableTest {
    Movable notMovable;
    Movable movingMovable;

    private void makeNotMovingMovable() {
        notMovable = new Movable() {
            @Override
            public boolean isMoving() {
                return false;
            }

            @Override
            public Direction getFacingDirection() {
                return Direction.DOWN;
            }
        };
    }

    private void makeMovingMovable() {
        movingMovable = new Movable() {
            @Override
            public boolean isMoving() {
                return true;
            }

            @Override
            public Direction getFacingDirection() {
                return Direction.DOWN;
            }
        };


    }
    @Test
    void testIsMoving() {
        makeMovingMovable();
        assertTrue(movingMovable.isMoving());
    }

    @Test
    void testIsNotMoving() {
        makeNotMovingMovable();
        assertFalse(notMovable.isMoving());
    }

    @Test
    void testGetFacingDirection() {
        makeMovingMovable();
        assertEquals(Direction.DOWN, movingMovable.getFacingDirection());
    }

    @Test
    void testIsAttacking() {
        makeMovingMovable();
        assertFalse(movingMovable.isAttacking());
    }

    @Test
    void testGetAttackFrame() {
        makeMovingMovable();
        assertEquals(1, movingMovable.getAttackFrame());
    }
}