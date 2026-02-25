package inf112.skeleton.model.entities;

import inf112.skeleton.model.world.GameWorld;

public abstract class Entity implements Collidable {
    protected float x, y, width, height, velocityX, velocityY;
    protected int health, speed;

    public Entity(float x, float y, float width, float height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    @Override
    public float getX() { return x; }

    @Override
    public float getY() { return y; }

    @Override
    public float getWidth() { return width; }

    @Override
    public float getHeight() { return height; }

    public int getSpeed() { return speed; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void step(GameWorld world, double deltaTime);

    public abstract String getTextureName();
}
