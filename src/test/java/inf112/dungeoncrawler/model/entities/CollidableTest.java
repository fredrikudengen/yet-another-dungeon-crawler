package inf112.dungeoncrawler.model.entities;

import static org.junit.jupiter.api.Assertions.*;

import inf112.dungeoncrawler.model.entities.enemies.NormalEnemy;
import inf112.dungeoncrawler.model.sound.SoundService;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollidableTest {

    private Room room;
    @SuppressWarnings("unused")
    private GameWorld world;
    private Player player;
    private NormalEnemy normalEnemy;
    private static final SoundService NO_SOUND = effect -> {};

    @BeforeEach
    void setUp() {
        room = new Room(20, 20);
        world = new GameWorld(room, NO_SOUND);
        player = new Player(300, 300, NO_SOUND);
        normalEnemy = new NormalEnemy(300,300, player);
    }

    @Test
    void testCollidesWith_True() {
        assertTrue(player.collidesWith(normalEnemy));
    }

    @Test
    void testCollidesWith_False() {
        normalEnemy.setPosition(500, 500);
        assertFalse(player.collidesWith(normalEnemy));
    }

    @Test
    void testCollidesEdge() {
        normalEnemy.setPosition(player.getX() + player.getWidth(), player.getY() + player.getHeight());
        assertFalse(player.collidesWith(normalEnemy));
    }
}
