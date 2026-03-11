package inf112.skeleton.model.entities;

public interface Collidable {
    float getX();
    float getY();
    float getWidth();
    float getHeight();

    default boolean collidesWith(Collidable other) {
        return this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY();
    }

    default void onCollision(Entity entity) {};
}