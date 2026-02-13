package inf112.skeleton.model.entities;

public interface Collidable {
    float getX();
    float getY();
    float getWidth();
    float getHeight();

    // Default method - added in Java 8
    default boolean collidesWith(Collidable other) {
        return this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY();
    }
}