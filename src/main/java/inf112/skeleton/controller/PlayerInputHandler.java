package inf112.skeleton.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import inf112.skeleton.model.entities.Player;

public class PlayerInputHandler extends InputAdapter {
    private final Player player;

    public PlayerInputHandler(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A, Input.Keys.LEFT -> player.setMovingLeft(true);
            case Input.Keys.D, Input.Keys.RIGHT -> player.setMovingRight(true);
            case Input.Keys.W, Input.Keys.UP -> player.setMovingUp(true);
            case Input.Keys.S, Input.Keys.DOWN -> player.setMovingDown(true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A, Input.Keys.LEFT -> player.setMovingLeft(false);
            case Input.Keys.D, Input.Keys.RIGHT -> player.setMovingRight(false);
            case Input.Keys.W, Input.Keys.UP -> player.setMovingUp(false);
            case Input.Keys.S, Input.Keys.DOWN -> player.setMovingDown(false);
        }
        return true;
    }
}
