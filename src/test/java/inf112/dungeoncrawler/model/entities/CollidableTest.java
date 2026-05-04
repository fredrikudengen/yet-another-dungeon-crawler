package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.entities.enemies.NormalEnemy;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollidableTest {

    private static final SoundService NO_SOUND = new SoundService() {
        @Override
        public void play(SoundEffect effect) { /* test */ }

        @Override
        public void dispose() { /* test */ }
    };

    private Player player;
    private NormalEnemy normalEnemy;

    @BeforeEach
    void setUp() {
        player      = new Player(300, 300, NO_SOUND);
        normalEnemy = new NormalEnemy(300, 300, player);
    }

    @Test
    void collidesWithSamePositionReturnsTrue() {
        assertTrue(player.collidesWith(normalEnemy));
    }

    @Test
    void collidesWithFarAwayReturnsFalse() {
        normalEnemy.setPosition(500, 500);
        assertFalse(player.collidesWith(normalEnemy));
    }

    @Test
    void collidesWithEdgeReturnsFalse() {
        normalEnemy.setPosition(
                player.getX() + player.getWidth(),
                player.getY() + player.getHeight());
        assertFalse(player.collidesWith(normalEnemy));
    }

    @Test
    void collidesWithPartialOverlapXReturnsTrue() {
        normalEnemy.setPosition(player.getX() + player.getWidth() - 1, player.getY());
        assertTrue(player.collidesWith(normalEnemy));
    }

    @Test
    void collidesWithPartialOverlapYReturnsTrue() {
        normalEnemy.setPosition(player.getX(), player.getY() + player.getHeight() - 1);
        assertTrue(player.collidesWith(normalEnemy));
    }
}