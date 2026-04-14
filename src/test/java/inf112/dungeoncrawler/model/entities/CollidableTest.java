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
        public void play(SoundEffect effect) { /* ingenting */ }

        @Override
        public void dispose() { /* ingenting */ }
    };

    private Player player;
    private NormalEnemy normalEnemy;

    @BeforeEach
    void setUp() {
        player      = new Player(300, 300, NO_SOUND);
        normalEnemy = new NormalEnemy(300, 300, player);
    }

    @Test
    void collidesWith_samePosition_returnsTrue() {
        assertTrue(player.collidesWith(normalEnemy));
    }

    @Test
    void collidesWith_farAway_returnsFalse() {
        normalEnemy.setPosition(500, 500);
        assertFalse(player.collidesWith(normalEnemy));
    }

    @Test
    void collidesWith_exactEdge_returnsFalse() {
        normalEnemy.setPosition(
                player.getX() + player.getWidth(),
                player.getY() + player.getHeight());
        assertFalse(player.collidesWith(normalEnemy));
    }

    @Test
    void collidesWith_partialOverlapX_returnsTrue() {
        normalEnemy.setPosition(player.getX() + player.getWidth() - 1, player.getY());
        assertTrue(player.collidesWith(normalEnemy));
    }

    @Test
    void collidesWith_partialOverlapY_returnsTrue() {
        normalEnemy.setPosition(player.getX(), player.getY() + player.getHeight() - 1);
        assertTrue(player.collidesWith(normalEnemy));
    }
}