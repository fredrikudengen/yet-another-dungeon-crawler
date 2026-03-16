package inf112.dungeoncrawler.model.entities;

import static org.junit.jupiter.api.Assertions.*;

import inf112.dungeoncrawler.model.entities.enemies.NormalEnemy;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollidableTest {

    private Room room;
    private GameWorld world;
    private Player player;
    private NormalEnemy normalEnemy;

    @BeforeEach
    void setUp() {
        room = new Room(20, 20);
        world = new GameWorld(room);
        player = new Player(300, 300);
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
