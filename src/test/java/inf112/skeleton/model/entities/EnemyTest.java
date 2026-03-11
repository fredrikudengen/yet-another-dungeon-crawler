package inf112.skeleton.model.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inf112.skeleton.model.world.GameWorld;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.model.world.TileType;

class EnemyTest {

    private Room room;
    private GameWorld world;
    private Player player;

    @BeforeEach
    void setUp() {
        room = new Room(20, 20);
        world = new GameWorld(room);
        player = new Player(400, 300);
    }

    @Test
    void testEnemyGetTextureNameReturnsEmptyString() {
        Enemy enemy = new Enemy(100, 100, 32, 32, 50, 5, player);
        assertEquals("", enemy.getTextureName());
    }

    @Test
    void testStepMovesEnemyTowardPlayer() {
        Enemy enemy = new Enemy(100, 300, 32, 32, 50, 5, player);
        float initialX = enemy.getX();
        enemy.step(world, 1.0);
        assertTrue(enemy.getX() > initialX, "Enemy should move right toward player");
    }

    @Test
    void testStepReducesPlayerHealthOnOverlap() {
        Enemy enemy = new Enemy(400, 295, 32, 32, 50, 5, player);
        int healthBefore = player.getHealth();
        enemy.step(world, 0.1);
        assertTrue(player.getHealth() < healthBefore, "Player health should decrease on collision");
    }

    @Test
    void testStepAtExactPlayerPositionDoesNotMove() {
        Enemy enemy = new Enemy(400, 300, 32, 32, 50, 5, player);
        float initialX = enemy.getX();
        float initialY = enemy.getY();
        enemy.step(world, 1.0);
        assertEquals(initialX, enemy.getX(), "X should not change when distance to player is zero");
        assertEquals(initialY, enemy.getY(), "Y should not change when distance to player is zero");
    }

    @Test
    void testStepDoesNotMoveThroughWall() {
        room.setTileAt(5, 9, TileType.WALL);
        Enemy enemy = new Enemy(100, 300, 32, 32, 50, 5, player);
        enemy.step(world, 1.0);
        assertEquals(100f, enemy.getX(), "Enemy should not move through a wall tile");
    }

    @Test
    void testFastEnemyGetTextureNameReturnsBlueJpg() {
        FastEnemy fastEnemy = new FastEnemy(100, 100, 32, 32, 100, 5, player);
        assertEquals("blue.jpg", fastEnemy.getTextureName());
    }
}
