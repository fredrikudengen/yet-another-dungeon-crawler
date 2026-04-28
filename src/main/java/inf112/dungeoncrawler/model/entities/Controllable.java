package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;

/**
 * Represents an entity whose movement and attacks can be driven by external
 * input.
 */
public interface Controllable {

    /** @param moving true to start moving left, false to stop */
    void setMovingLeft(boolean moving);

    /** @param moving true to start moving right, false to stop */
    void setMovingRight(boolean moving);

    /** @param moving true to start moving up, false to stop */
    void setMovingUp(boolean moving);

    /** @param moving true to start moving down, false to stop */
    void setMovingDown(boolean moving);

    /**
     * Triggers an attack in the given direction if the attack cooldown allows it.
     *
     * @param direction the direction to attack towards
     */
    void performAttack(Direction direction);
}