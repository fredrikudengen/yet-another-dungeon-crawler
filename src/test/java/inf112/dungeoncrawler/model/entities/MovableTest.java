package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovableTest {

    @Test
    void testIsMoving() {
        Movable movable = new Movable() {
            @Override
            public boolean isMoving() {
                return true;
            }

            @Override
            public Direction getFacingDirection() {
                return Direction.UP;
            }
        };

        assertTrue(movable.isMoving());
    }

    @Test
    void testGetFacingDirection() {
        Movable movable = new Movable() {
            @Override
            public boolean isMoving() {
                return false;
            }

            @Override
            public Direction getFacingDirection() {
                return Direction.DOWN;
            }
        };

        assertEquals(Direction.DOWN, movable.getFacingDirection());
    }

    @Test
    void testIsAttacking() {
        Movable movable = new Movable() {
            @Override
            public boolean isMoving() {
                return false;
            }

            @Override
            public Direction getFacingDirection() {
                return Direction.UP;
            }
        };

        assertFalse(movable.isAttacking());
    }

    @Test
    void testGetAttackFrame() {
        Movable movable = new Movable() {
            @Override
            public boolean isMoving() {
                return false;
            }

            @Override
            public Direction getFacingDirection() {
                return Direction.UP;
            }
        };

        assertEquals(1, movable.getAttackFrame());
    }
}