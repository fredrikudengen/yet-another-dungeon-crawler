package inf112.dungeoncrawler.model.entities;

/**
 * Represents an entity that can be targeted and damaged by enemies.
 * Extends Collidable so enemies can detect proximity and collisions.
 */
public interface Targetable extends Collidable {

    /**
     * Returns true if the target is currently invincible and should not take damage.
     */
    boolean isInvincible();

    /**
     * Applies a knockback impulse to this target.
     *
     * @param vx horizontal velocity component of the knockback
     * @param vy vertical velocity component of the knockback
     */
    void applyKnockback(float vx, float vy);

    /**
     * Returns the amount of knockback force applied when this target is hit.
     */
    float getKnockBackForce();
}