package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;

public interface Movable {

    /**
     * Used by animation to check if sprite needs to update.
     */
    boolean isMoving();

    /**
     * Used by animation to check which sprite to render.
     *
     * @return Direction entity is facing
     */
    Direction getFacingDirection();

    /**
     * Used by animation to check if attack animation needs to render.
     */
    default boolean isAttacking()  { return false; }

    /**
     *
     * @return
     */
    default int getAttackFrame()   { return 1; }
}