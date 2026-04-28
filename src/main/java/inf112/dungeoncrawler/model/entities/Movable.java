package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;

/**
 * Represents an entity that moves and has a facing direction.
 */
public interface Movable {

    /**
     * Returns true if the entity is currently moving.
     * Used by the animation system to decide whether to advance the walk cycle.
     */
    boolean isMoving();

    /**
     * Returns the direction the entity is currently facing.
     * Used by the animation system to select the correct directional sprite.
     */
    Direction getFacingDirection();

    /**
     * Returns true if the entity is currently performing an attack animation.
     * Defaults to false for non-attacking entities.
     */
    default boolean isAttacking() { return false; }

    /**
     * Returns the current attack animation frame index (1 or 2).
     * Only relevant when isAttacking() returns true.
     */
    default int getAttackFrame() { return 1; }

    /**
     * Returns the duration in seconds of each walk animation frame.
     * Override to customise animation speed per entity type.
     *
     * @return seconds per frame; default is 0.18
     */
    default float getFrameDuration() { return 0.18f; }
}