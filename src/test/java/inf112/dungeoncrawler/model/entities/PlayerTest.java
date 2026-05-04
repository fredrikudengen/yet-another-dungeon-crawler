package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.entities.enemies.Enemy;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private static final SoundService NO_SOUND = new SoundService() {
        @Override
        public void play(SoundEffect effect) { /* test */ }

        @Override
        public void dispose() { /* test */ }
    };

    private GameWorld world;
    private Player player;

    @BeforeEach
    void setUp() {
        Room room = new Room(20, 20);
        player = new Player(300, 300, NO_SOUND);
        world  = new GameWorld(room, player);
        player.setHealth(5);
    }

    @Test
    void stepNoMovement() {
        float x = player.getX();
        float y = player.getY();
        player.step(world, 1.0);
        assertEquals(x, player.getX());
        assertEquals(y, player.getY());
    }

    @Test
    void stepMovingRightIncreasesX() {
        float initialX = player.getX();
        player.setMovingRight(true);
        player.step(world, 1.0);
        assertTrue(player.getX() > initialX);
    }

    @Test
    void stepMovingLeftDecreasesX() {
        float initialX = player.getX();
        player.setMovingLeft(true);
        player.step(world, 1.0);
        assertTrue(player.getX() < initialX);
    }

    @Test
    void stepMovingUpIncreasesY() {
        float initialY = player.getY();
        player.setMovingUp(true);
        player.step(world, 1.0);
        assertTrue(player.getY() > initialY);
    }

    @Test
    void stepMovingDownDecreasesY() {
        float initialY = player.getY();
        player.setMovingDown(true);
        player.step(world, 1.0);
        assertTrue(player.getY() < initialY);
    }

    @Test
    void isMovingWhenMovingRightReturnsTrue() {
        player.setMovingRight(true);
        assertTrue(player.isMoving());
    }

    @Test
    void isMovingWhenNotMovingReturnsFalse() {
        assertFalse(player.isMoving());
    }

    @Test
    void getFacingDirectionAfterMovingLeft() {
        player.setMovingLeft(true);
        player.step(world, 0.016);
        assertEquals(Direction.LEFT, player.getFacingDirection());
    }

    @Test
    void getFacingDirectionAfterMovingRight() {
        player.setMovingRight(true);
        player.step(world, 0.016);
        assertEquals(Direction.RIGHT, player.getFacingDirection());
    }

    @Test
    void takeDamageReducesHealth() {
        player.takeDamage(2, 0, 0);
        assertEquals(3, player.getHealth());
    }

    @Test
    void takeDamageSetsInvincible() {
        player.takeDamage(1, 0, 0);
        assertTrue(player.isInvincible());
    }

    @Test
    void takeDamageWhileInvincibleDoesNotReduceHealth() {
        player.takeDamage(1, 0, 0);
        int healthAfterFirst = player.getHealth();
        player.takeDamage(1, 0, 0);
        assertEquals(healthAfterFirst, player.getHealth());
    }

    @Test
    void invincibilityExpiresAfterDuration() {
        player.takeDamage(1, 0, 0);
        player.step(world, Player.INVINCIBILITY_DURATION + 0.1);
        assertFalse(player.isInvincible());
    }

    @Test
    void performAttackSetsIsAttacking() {
        player.performAttack(Direction.RIGHT);
        assertTrue(player.isAttacking());
    }

    @Test
    void performAttackSetsFacingDirection() {
        player.performAttack(Direction.UP);
        assertEquals(Direction.UP, player.getFacingDirection());
    }

    @Test
    void getAttackFrameEarlyInAttackReturnsOne() {
        player.performAttack(Direction.RIGHT);
        assertEquals(1, player.getAttackFrame());
    }

    @Test
    void performAttackWhileAttackingIsIgnored() {
        player.performAttack(Direction.RIGHT);
        player.performAttack(Direction.LEFT);
        assertEquals(Direction.RIGHT, player.getFacingDirection());
    }

    @Test
    void performAttackHitsEnemy() {
        Enemy enemy = makeTestEnemy(player.getX() + 140, player.getY());
        world.addEntity(enemy);

        int initialHealth = enemy.getHealth();
        player.performAttack(Direction.RIGHT);
        player.step(world, 0.016);

        assertTrue(enemy.getHealth() < initialHealth);
    }

    @Test
    void performAttackEnemyEnteringRangeDuringSwingGetsHit() {
        Enemy enemy = makeTestEnemy(player.getX() + 250, player.getY());
        world.addEntity(enemy);

        int initialHealth = enemy.getHealth();
        player.performAttack(Direction.RIGHT);
        player.step(world, 0.05);

        enemy.setPosition(player.getX() + 140, player.getY());
        player.step(world, 0.05);

        assertTrue(enemy.getHealth() < initialHealth);
    }

    @Test
    void collidesWithOverlappingPlayerReturnsTrue() {
        Player other = new Player(310, 310, NO_SOUND);
        assertTrue(player.collidesWith(other));
    }

    @Test
    void collidesWithFarAwayPlayerReturnsFalse() {
        Player other = new Player(1000, 1000, NO_SOUND);
        assertFalse(player.collidesWith(other));
    }

    private Enemy makeTestEnemy(float x, float y) {
        return new Enemy(x, y, player) {
            {
                this.health = 3;
                this.damage = 1;
                this.speed = 0;
                this.knockBackForce = 0;
            }

            @Override
            public void step(GameWorld world, double deltaTime) {
                // test
            }
        };
    }
}