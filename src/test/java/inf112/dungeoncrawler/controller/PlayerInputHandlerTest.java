package inf112.dungeoncrawler.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inf112.dungeoncrawler.model.entities.Player;

public class PlayerInputHandlerTest {

    private PlayerInputHandler playerInputHandler;
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(100, 100);
        playerInputHandler = new PlayerInputHandler(player);
    }
    @Test
    void keyDown_W_setsMovingUp() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.W);
        assertTrue(player.isMovingUp());
    }

    @Test
    void keyDown_S_setsMovingDown() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.S);
        assertTrue(player.isMovingDown());
    }

    @Test
    void keyDown_A_setsMovingLeft() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.A);
        assertTrue(player.isMovingLeft());
    }

    @Test
    void keyDown_D_setsMovingRight() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.D);
        assertTrue(player.isMovingRight());
    }

    @Test
    void keyUp_W_clearsMovingUp() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.W);
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.W);
        assertFalse(player.isMovingUp());
    }

    @Test
    void keyUp_S_clearsMovingDown() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.S);
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.S);
        assertFalse(player.isMovingDown());
    }

    @Test
    void keyUp_A_clearsMovingLeft() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.A);
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.A);
        assertFalse(player.isMovingLeft());
    }

    @Test
    void keyUp_D_clearsMovingRight() {
        playerInputHandler.keyDown(com.badlogic.gdx.Input.Keys.D);
        playerInputHandler.keyUp(com.badlogic.gdx.Input.Keys.D);
        assertFalse(player.isMovingRight());
    }

    @Test
    void noButtonNoMovement() {
        assertFalse(player.isMovingUp());
        assertFalse(player.isMovingDown());
        assertFalse(player.isMovingLeft());
        assertFalse(player.isMovingRight());
    }
}