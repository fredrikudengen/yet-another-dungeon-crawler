package inf112.dungeoncrawler.model.entities;

import static org.junit.jupiter.api.Assertions.*;

import inf112.dungeoncrawler.model.sound.SoundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;

class PlayerTest {

    private Room room;
    private GameWorld world;
    private Player player;
    private static final SoundService NO_SOUND = effect -> {};

    @BeforeEach
    void setUp() {
        room = new Room(20, 20);
        world = new GameWorld(room, NO_SOUND);
        player = new Player(300, 300, NO_SOUND);
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
    void testCollidesWithReturnsTrueOnOverlap() {
        Player other = new Player(310, 310, NO_SOUND);
        assertTrue(player.collidesWith(other));
    }

    @Test
    void testCollidesWithReturnsFalseWhenFarAway() {
        Player other = new Player(1000, 1000, NO_SOUND);
        assertFalse(player.collidesWith(other));
    }

    @Test
    void testTakeDamageReducesHealth() {
        int initialHealth = player.getHealth();
        player.takeDamage(2,0 ,0);
        assertEquals(initialHealth - 2, player.getHealth());
    }
}