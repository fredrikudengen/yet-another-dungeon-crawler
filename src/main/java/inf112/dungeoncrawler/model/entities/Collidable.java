package inf112.dungeoncrawler.model.entities;

/**
 * Represents a rectangular object that can participate in collision detection.
 * All entities in the game implement this interface.
 */
public interface Collidable {

    /** @return the left edge x-coordinate in world pixels */
    float getX();

    /** @return the bottom edge y-coordinate in world pixels */
    float getY();

    /** @return the width of the bounding box in pixels */
    float getWidth();

    /** @return the height of the bounding box in pixels */
    float getHeight();

    /**
     * Returns true if this object's bounding box overlaps with others.
     *
     * @param other the collidable to test against
     * @return true if the two bounding boxes intersect
     */
    default boolean collidesWith(Collidable other) {
        return this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY();
    }

    /**
     * Called when this object collides with {@code entity}.
     * Default implementation does nothing; override to define collision behaviour.
     *
     * @param entity the entity this object has collided with
     */
    default void onCollision(Entity entity) {}
}