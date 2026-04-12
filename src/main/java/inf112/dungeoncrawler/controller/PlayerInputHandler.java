package inf112.dungeoncrawler.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.entities.Controllable;

import java.util.Objects;

public class PlayerInputHandler extends InputAdapter {
    private final Controllable player;

    /**
     * Reads input from keyboard and sets flags for Player.
     *
     * @param player object where flags are set.
     */
    public PlayerInputHandler(Controllable player) {
        this.player = Objects.requireNonNull(player, "player must not be null");
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A -> player.setMovingLeft(true);
            case Input.Keys.D -> player.setMovingRight(true);
            case Input.Keys.W -> player.setMovingUp(true);
            case Input.Keys.S -> player.setMovingDown(true);

            case Input.Keys.UP    -> player.performAttack(Direction.UP);
            case Input.Keys.DOWN  -> player.performAttack(Direction.DOWN);
            case Input.Keys.LEFT  -> player.performAttack(Direction.LEFT);
            case Input.Keys.RIGHT -> player.performAttack(Direction.RIGHT);

            default -> { return false; }
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

            default -> { return false; }
        }
        return true;
    }
}