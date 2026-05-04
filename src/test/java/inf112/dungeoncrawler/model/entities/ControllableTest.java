package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllableTest {

    private static final SoundService NO_SOUND = new SoundService() {
        @Override
        public void play(SoundEffect effect) { /* test */ }

        @Override
        public void dispose() { /* test */ }
    };

    private Player player; 

    @BeforeEach
    void setUp() {
        player = new Player(300, 300, NO_SOUND); 
    }

    @Test
    void setMovingLeft() {
        player.setMovingLeft(true);
        assertTrue(player.isMoving());

        player.setMovingLeft(false);
        assertFalse(player.isMoving());
    }

    @Test
    void setMovingRight() {
        player.setMovingRight(true);
        assertTrue(player.isMoving());

        player.setMovingRight(false);
        assertFalse(player.isMoving());
    }

    @Test
    void setMovingUp() {
        player.setMovingUp(true);
        assertTrue(player.isMoving());

        player.setMovingUp(false);
        assertFalse(player.isMoving());
    }

    @Test
    void setMovingDown() {
        player.setMovingDown(true);
        assertTrue(player.isMoving());

        player.setMovingDown(false);
        assertFalse(player.isMoving());
    }

    @Test
    void performAttack() {
        player.performAttack(Direction.UP);
        assertTrue(player.isAttacking());
    }
}
