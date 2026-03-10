package inf112.skeleton.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inf112.skeleton.model.entities.Player;

public class PlayerInputHandlerTest {

    private PlayerInputHandler playerInputHandler;
    private Player player;

    @BeforeEach
    void setUp() {
        // Create a player with a starting position
        player = new Player(100, 100);
        playerInputHandler = new PlayerInputHandler(player);
    }

    @Test
    void testMoveUp() {
        // Simulate key press for up
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.W);
        
        // Check that movingUp is set to true
        assertTrue(player.isMovingUp());
        
        // Release the key
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.W);
        
        // Check that movingUp is set to false
        assertFalse(player.isMovingUp());
    }

    @Test
    void testMoveDown() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.S);
        assertTrue(player.isMovingDown());
        
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.S);
        assertFalse(player.isMovingDown());
    }

    @Test
    void testMoveLeft() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.A);
        assertTrue(player.isMovingLeft());
        
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.A);
        assertFalse(player.isMovingLeft());
    }

    @Test
    void testMoveRight() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.D);
        assertTrue(player.isMovingRight());
        
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.D);
        assertFalse(player.isMovingRight());
    }

    @Test
    void testWASDKeys() {
        // Test W for up
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.W);
        assertTrue(player.isMovingUp());
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.W);
        assertFalse(player.isMovingUp());

        // Test A for left
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.A);
        assertTrue(player.isMovingLeft());
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.A);
        assertFalse(player.isMovingLeft());

        // Test S for down
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.S);
        assertTrue(player.isMovingDown());
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.S);
        assertFalse(player.isMovingDown());

        // Test D for right
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.D);
        assertTrue(player.isMovingRight());
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.D);
        assertFalse(player.isMovingRight());
    }
}