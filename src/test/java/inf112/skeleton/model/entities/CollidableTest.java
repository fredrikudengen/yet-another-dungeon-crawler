package inf112.skeleton.model.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollidableTest {

    private Collidable collidable1;
    private Collidable collidable2;

    @BeforeEach
    void setUp() {
        // Create mock implementations of Collidable for testing
        collidable1 = new Collidable() {
            @Override
            public float getX() {
                return 0;
            }

            @Override
            public float getY() {
                return 0;
            }

            @Override
            public float getWidth() {
                return 10;
            }

            @Override
            public float getHeight() {
                return 10;
            }
        };

        collidable2 = new Collidable() {
            @Override
            public float getX() {
                return 5;
            }

            @Override
            public float getY() {
                return 5;
            }

            @Override
            public float getWidth() {
                return 10;
            }

            @Override
            public float getHeight() {
                return 10;
            }
        };
    }

    @Test
    void testCollidesWith_True() {
        // collidable1 and collidable2 overlap
        assertTrue(collidable1.collidesWith(collidable2));
    }

    @Test
    void testCollidesWith_False() {
        // Move collidable2 far away so they don't overlap
        Collidable collidable3 = new Collidable() {
            @Override
            public float getX() {
                return 20;
            }

            @Override
            public float getY() {
                return 20;
            }

            @Override
            public float getWidth() {
                return 10;
            }

            @Override
            public float getHeight() {
                return 10;
            }
        };

        assertFalse(collidable1.collidesWith(collidable3));
    }

    @Test
    void testCollidesWith_EdgeTouching() {
        // Move collidable2 so it just touches the edge of collidable1
        Collidable collidable4 = new Collidable() {
            @Override
            public float getX() {
                return 10;
            }

            @Override
            public float getY() {
                return 10;
            }

            @Override
            public float getWidth() {
                return 10;
            }

            @Override
            public float getHeight() {
                return 10;
            }
        };

        // Edge touching should not count as a collision
        assertFalse(collidable1.collidesWith(collidable4));
    }
}
