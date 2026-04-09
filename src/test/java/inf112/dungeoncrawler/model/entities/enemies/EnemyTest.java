package inf112.dungeoncrawler.model.entities.enemies;

import static org.junit.jupiter.api.Assertions.*;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.model.world.TileType;
import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;

class EnemyTest {

    private Room room;
    private GameWorld world;
    private Player player;
    private static final SoundService NO_SOUND = effect -> {};

    @BeforeEach
    void setUp() {
        room = new Room(20, 20);
        world = new GameWorld(room, player);
        player = new Player(400, 300, NO_SOUND);
    }

    @Test
    void testEnemyGetTextureNameReturnsEmptyString() {
        Enemy enemy = new Enemy(100, 100, player) {
            @Override
            public boolean isMoving() {
                return false;
            }

            @Override
            public Direction getFacingDirection() {
                return null;
            }

            @Override
            public void onCollision(Entity entity) {
            }

            @Override
            public String getBaseTexturePath() {
                return "test";
            }
        };
        assertEquals("test", enemy.getBaseTexturePath());
    }

    @Test
    void testStepMovesEnemyTowardPlayer() {
        Enemy enemy = new NormalEnemy(100, 300, player);

        float initialX = enemy.getX();
        enemy.step(world, 1.0);
        assertTrue(enemy.getX() > initialX, "Enemy should move right toward player");
    }

    @Test
    void testStepAtExactPlayerPositionDoesNotMove() {
        player.setPosition(100, 100);

        Enemy enemy = new Enemy(100, 100, player) {
            @Override
            public boolean isMoving() {
                return false;
            }

            @Override
            public Direction getFacingDirection() {
                return null;
            }

            @Override
            public void onCollision(Entity entity) {
            }

            @Override
            public String getBaseTexturePath() {
                return "test";
            }
        };
        float initialX = enemy.getX();
        float initialY = enemy.getY();
        enemy.step(world, 1.0);
        assertEquals(initialX, enemy.getX(), "X should not change when distance to player is zero");
        assertEquals(initialY, enemy.getY(), "Y should not change when distance to player is zero");
    }

    @Test
    void testStepDoesNotMoveThroughWall() {
        room.setTileAt(5, 9, TileType.WALL);
        player.setPosition(500, 300);

        Enemy enemy = new Enemy(100, 300, player) {
            @Override
            public boolean isMoving() {
                return false;
            }

            @Override
            public Direction getFacingDirection() {
                return null;
            }

            @Override
            public void onCollision(Entity entity) {
            }

            @Override
            public String getBaseTexturePath() {
                return "test";
            }
        };
        enemy.step(world, 1.0);
        assertEquals(100f, enemy.getX(), "Enemy should not move through a wall tile");
    }
}