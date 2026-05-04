package inf112.dungeoncrawler.controller;

import com.badlogic.gdx.Input;
import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.entities.Controllable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerInputHandlerTest {

    private static class ControllableStub implements Controllable {
        boolean movingLeft, movingRight, movingUp, movingDown;
        Direction lastAttackDirection = null;

        @Override public void setMovingLeft(boolean v)  { movingLeft  = v; }
        @Override public void setMovingRight(boolean v) { movingRight = v; }
        @Override public void setMovingUp(boolean v)    { movingUp    = v; }
        @Override public void setMovingDown(boolean v)  { movingDown  = v; }
        @Override public void performAttack(Direction d){ lastAttackDirection = d; }
    }

    private PlayerInputHandler handler;
    private ControllableStub stub;

    @BeforeEach
    void setUp() {
        stub    = new ControllableStub();
        handler = new PlayerInputHandler(stub);
    }

    @Test
    void keyDown_W_setsMovingUp() {
        handler.keyDown(Input.Keys.W);
        assertTrue(stub.movingUp);
    }

    @Test
    void keyDown_S_setsMovingDown() {
        handler.keyDown(Input.Keys.S);
        assertTrue(stub.movingDown);
    }

    @Test
    void keyDown_A_setsMovingLeft() {
        handler.keyDown(Input.Keys.A);
        assertTrue(stub.movingLeft);
    }

    @Test
    void keyDown_D_setsMovingRight() {
        handler.keyDown(Input.Keys.D);
        assertTrue(stub.movingRight);
    }

    @Test
    void keyUp_W_clearsMovingUp() {
        handler.keyDown(Input.Keys.W);
        handler.keyUp(Input.Keys.W);
        assertFalse(stub.movingUp);
    }

    @Test
    void keyUp_S_clearsMovingDown() {
        handler.keyDown(Input.Keys.S);
        handler.keyUp(Input.Keys.S);
        assertFalse(stub.movingDown);
    }

    @Test
    void keyUp_A_clearsMovingLeft() {
        handler.keyDown(Input.Keys.A);
        handler.keyUp(Input.Keys.A);
        assertFalse(stub.movingLeft);
    }

    @Test
    void keyUp_D_clearsMovingRight() {
        handler.keyDown(Input.Keys.D);
        handler.keyUp(Input.Keys.D);
        assertFalse(stub.movingRight);
    }

    @Test
    void keyDown_rightArrow_performsAttackRight() {
        handler.keyDown(Input.Keys.RIGHT);
        assertEquals(Direction.RIGHT, stub.lastAttackDirection);
    }

    @Test
    void keyDown_leftArrow_performsAttackLeft() {
        handler.keyDown(Input.Keys.LEFT);
        assertEquals(Direction.LEFT, stub.lastAttackDirection);
    }

    @Test
    void keyDown_upArrow_performsAttackUp() {
        handler.keyDown(Input.Keys.UP);
        assertEquals(Direction.UP, stub.lastAttackDirection);
    }

    @Test
    void keyDown_downArrow_performsAttackDown() {
        handler.keyDown(Input.Keys.DOWN);
        assertEquals(Direction.DOWN, stub.lastAttackDirection);
    }

    @Test
    void keyDown_handledKey_returnsTrue() {
        assertTrue(handler.keyDown(Input.Keys.W));
    }

    @Test
    void keyDown_unhandledKey_returnsFalse() {
        assertFalse(handler.keyDown(Input.Keys.SPACE));
    }

    @Test
    void keyUp_handledKey_returnsTrue() {
        assertTrue(handler.keyUp(Input.Keys.W));
    }

    @Test
    void keyUp_unhandledKey_returnsFalse() {
        assertFalse(handler.keyUp(Input.Keys.SPACE));
    }

    @Test
    void constructor_nullPlayer_throwsNullPointerException() {
        // this code was adapted using Claude Sonnet 4.6
        assertThrows(NullPointerException.class, () -> new PlayerInputHandler(null));
    }
}