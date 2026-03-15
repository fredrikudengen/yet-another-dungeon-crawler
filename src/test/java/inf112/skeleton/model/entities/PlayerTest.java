package inf112.skeleton.model.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inf112.skeleton.model.Direction;
import inf112.skeleton.model.world.GameWorld;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.model.entities.enemies.NormalEnemy;
import inf112.skeleton.model.entities.enemies.Enemy;

class PlayerTest {

    private Room room;
    private GameWorld world;
    private Player player;

    @BeforeEach
    void setUp() {
        room = new Room(20, 20);
        world = new GameWorld(room);
        player = new Player(300, 300);
        player.health = 5;
    }

    @Test
    void testNoMovementWhenNoButtonPressed() {
        float initialX = player.getX();
        float initialY = player.getY();
        player.step(world, 1.0);
        assertEquals(initialX, player.getX());
        assertEquals(initialY, player.getY());
    }

    @Test
    void testStepMovingRightIncreasesX() {
        float initialX = player.getX();
        player.setMovingRight(true);
        player.step(world, 1.0);
        assertTrue(player.getX() > initialX);
    }

    @Test
    void testStepMovingLeftDecreasesX() {
        float initialX = player.getX();
        player.setMovingLeft(true);
        player.step(world, 1.0);
        assertTrue(player.getX() < initialX);
    }

    @Test
    void testStepMovingUpIncreasesY() {
        float initialY = player.getY();
        player.setMovingUp(true);
        player.step(world, 1.0);
        assertTrue(player.getY() > initialY);
    }

    @Test
    void testStepMovingDownDecreasesY() {
        float initialY = player.getY();
        player.setMovingDown(true);
        player.step(world, 1.0);
        assertTrue(player.getY() < initialY);
    }

    @Test
    void testStepDoesNotMoveThroughRoomBoundary() {
        Player nearEdge = new Player(580, 300);
        nearEdge.setMovingRight(true);
        nearEdge.step(world, 1.0);
        assertEquals(580f, nearEdge.getX());
    }

    @Test
    void testCollidesWithReturnsTrueOnOverlap() {
        Player other = new Player(310, 310);
        assertTrue(player.collidesWith(other));
    }

    @Test
    void testCollidesWithReturnsFalseWhenFarAway() {
        Player other = new Player(1000, 1000);
        assertFalse(player.collidesWith(other));
    }

    @Test
    void testTakeDamageReducesHealth() {
        int initialHealth = player.getHealth();
        player.takeDamage(2);
        assertEquals(initialHealth - 2, player.getHealth());
    }
}