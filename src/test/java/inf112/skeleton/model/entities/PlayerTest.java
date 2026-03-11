package inf112.skeleton.model.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inf112.skeleton.model.Direction;
import inf112.skeleton.model.world.GameWorld;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.model.entities.enemies.NormalEnemy;
import inf112.skeleton.model.entities.enemies.Enemy;

class PlayerTest {

    private Room room;
    private GameWorld world;
    private Player player;

    @BeforeEach
    void setUp() {
        room = new Room(20, 20);
        world = new GameWorld(room);
        player = new Player(300, 300);
        // Sett health manuelt for tester
        player.health = 5;
    }

    @Test
    void testGetTextureNameReturnsObligatorPng() {
        assertEquals("src/main/resources/Player/Weapon1/Walk/Player_walk_weapon1_up1.png", 
            player.getTextureName());
    }

    @Test
    void testStepMovingRightIncreasesX() {
        float initialX = player.getX();
        player.setMovingRight(true);
        player.step(world, 1.0);
        assertTrue(player.getX() > initialX, "Moving right should increase X");
    }

    @Test
    void testStepMovingLeftDecreasesX() {
        float initialX = player.getX();
        player.setMovingLeft(true);
        player.step(world, 1.0);
        assertTrue(player.getX() < initialX, "Moving left should decrease X");
    }

    @Test
    void testStepMovingUpIncreasesY() {
        float initialY = player.getY();
        player.setMovingUp(true);
        player.step(world, 1.0);
        assertTrue(player.getY() > initialY, "Moving up should increase Y");
    }

    @Test
    void testStepMovingDownDecreasesY() {
        float initialY = player.getY();
        player.setMovingDown(true);
        player.step(world, 1.0);
        assertTrue(player.getY() < initialY, "Moving down should decrease Y");
    }

    @Test
    void testStepDoesNotMoveThroughRoomBoundary() {
        Player nearEdge = new Player(580, 300);
        nearEdge.setMovingRight(true);
        nearEdge.step(world, 1.0);
        assertEquals(580f, nearEdge.getX(), "Player should not move outside the room boundary");
    }

    @Test
    void testPerformAttackDamagesEnemyInRange() {
        // Bruk NormalEnemy i stedet for abstrakt Enemy
        Enemy enemy = new NormalEnemy(350, 300, player);
        world.addEntity(enemy);
        
        int healthBefore = enemy.getHealth();
        player.performAttack(Direction.RIGHT);
        player.step(world, 0.1); // Gir tid for attack å prosessere
        
        assertTrue(enemy.getHealth() < healthBefore, 
            "Attack should damage an enemy within range");
    }

    @Test
    void testMovingFlagsSettersAndGetters() {
        player.setMovingLeft(true);
        assertTrue(player.isMovingLeft());
        player.setMovingRight(true);
        assertTrue(player.isMovingRight());
        player.setMovingUp(true);
        assertTrue(player.isMovingUp());
        player.setMovingDown(true);
        assertTrue(player.isMovingDown());

        player.setMovingLeft(false);
        assertFalse(player.isMovingLeft());
        player.setMovingRight(false);
        assertFalse(player.isMovingRight());
        player.setMovingUp(false);
        assertFalse(player.isMovingUp());
        player.setMovingDown(false);
        assertFalse(player.isMovingDown());
    }

    @Test
    void testCollidesWithReturnsTrueOnOverlap() {
        Player other = new Player(310, 310);
        assertTrue(player.collidesWith(other), "Overlapping players should collide");
    }

    @Test
    void testTakeDamageReducesHealth() {
        int initialHealth = player.getHealth();
        player.takeDamage(2);
        assertEquals(initialHealth - 2, player.getHealth());
    }
}