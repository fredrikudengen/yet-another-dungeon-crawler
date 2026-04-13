package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;
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
        public void play(SoundEffect effect) { /* ingenting */ }

        @Override
        public void dispose() { /* ingenting */ }
    };

    private Room room;
    private GameWorld world;
    private Player player;

    @BeforeEach
    void setUp() {
        room   = new Room(20, 20);
        player = new Player(300, 300, NO_SOUND);
        world  = new GameWorld(room, player);
        player.setHealth(5);
    }

    @Test
    void step_noMovement_positionUnchanged() {
        float x = player.getX();
        float y = player.getY();
        player.step(world, 1.0);
        assertEquals(x, player.getX());
        assertEquals(y, player.getY());
    }

    @Test
    void step_movingRight_increasesX() {
        float initialX = player.getX();
        player.setMovingRight(true);
        player.step(world, 1.0);
        assertTrue(player.getX() > initialX);
    }

    @Test
    void step_movingLeft_decreasesX() {
        float initialX = player.getX();
        player.setMovingLeft(true);
        player.step(world, 1.0);
        assertTrue(player.getX() < initialX);
    }

    @Test
    void step_movingUp_increasesY() {
        float initialY = player.getY();
        player.setMovingUp(true);
        player.step(world, 1.0);
        assertTrue(player.getY() > initialY);
    }

    @Test
    void step_movingDown_decreasesY() {
        float initialY = player.getY();
        player.setMovingDown(true);
        player.step(world, 1.0);
        assertTrue(player.getY() < initialY);
    }

    @Test
    void isMoving_whenMovingRight_returnsTrue() {
        player.setMovingRight(true);
        assertTrue(player.isMoving());
    }

    @Test
    void isMoving_whenNotMoving_returnsFalse() {
        assertFalse(player.isMoving());
    }

    @Test
    void getFacingDirection_afterMovingLeft_returnsLeft() {
        player.setMovingLeft(true);
        player.step(world, 0.016);
        assertEquals(Direction.LEFT, player.getFacingDirection());
    }

    @Test
    void getFacingDirection_afterMovingRight_returnsRight() {
        player.setMovingRight(true);
        player.step(world, 0.016);
        assertEquals(Direction.RIGHT, player.getFacingDirection());
    }

    @Test
    void takeDamage_reducesHealth() {
        player.takeDamage(2, 0, 0);
        assertEquals(3, player.getHealth());
    }

    @Test
    void takeDamage_setsInvincible() {
        player.takeDamage(1, 0, 0);
        assertTrue(player.isInvincible());
    }

    @Test
    void takeDamage_whileInvincible_doesNotReduceHealth() {
        player.takeDamage(1, 0, 0);
        int healthAfterFirst = player.getHealth();
        player.takeDamage(1, 0, 0);
        assertEquals(healthAfterFirst, player.getHealth());
    }

    @Test
    void invincibility_expiresAfterDuration() {
        player.takeDamage(1, 0, 0);
        player.step(world, Player.INVINCIBILITY_DURATION + 0.1);
        assertFalse(player.isInvincible());
    }

    @Test
    void performAttack_setsIsAttacking() {
        player.performAttack(Direction.RIGHT);
        assertTrue(player.isAttacking());
    }

    @Test
    void performAttack_setsFacingDirection() {
        player.performAttack(Direction.UP);
        assertEquals(Direction.UP, player.getFacingDirection());
    }

    @Test
    void getAttackFrame_earlyInAttack_returnsOne() {
        player.performAttack(Direction.RIGHT);
        assertEquals(1, player.getAttackFrame());
    }

    @Test
    void performAttack_whileAttacking_isIgnored() {
        player.performAttack(Direction.RIGHT);
        player.performAttack(Direction.LEFT);
        assertEquals(Direction.RIGHT, player.getFacingDirection());
    }

    @Test
    void collidesWith_overlappingPlayer_returnsTrue() {
        Player other = new Player(310, 310, NO_SOUND);
        assertTrue(player.collidesWith(other));
    }

    @Test
    void collidesWith_farAwayPlayer_returnsFalse() {
        Player other = new Player(1000, 1000, NO_SOUND);
        assertFalse(player.collidesWith(other));
    }
}