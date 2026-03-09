package inf112.skeleton.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import inf112.skeleton.model.entities.Player;
import inf112.skeleton.model.Direction;

public class PlayerInputHandler extends InputAdapter {
    private final Player player;

    public PlayerInputHandler(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A -> player.setMovingLeft(true);
            case Input.Keys.D -> player.setMovingRight(true);
            case Input.Keys.W -> player.setMovingUp(true);
            case Input.Keys.S -> player.setMovingDown(true);

            case Input.Keys.UP -> player.performAttack(Direction.UP);
            case Input.Keys.DOWN -> player.performAttack(Direction.DOWN);
            case Input.Keys.LEFT -> player.performAttack(Direction.LEFT);
            case Input.Keys.RIGHT -> player.performAttack(Direction.RIGHT);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A -> player.setMovingLeft(false);
            case Input.Keys.D -> player.setMovingRight(false);
            case Input.Keys.W -> player.setMovingUp(false);
            case Input.Keys.S -> player.setMovingDown(false);
        }
        return true;
    }
}
