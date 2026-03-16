package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.world.GameWorld;

public abstract class Entity implements Collidable {
    protected float x, y, width, height, velocityX, velocityY;
    public int health;
    public int speed;
    public int damage;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() { return x; }

    @Override
    public float getY() { return y; }

    @Override
    public float getWidth() { return width; }

    @Override
    public float getHeight() { return height; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public abstract void step(GameWorld world, double deltaTime);

    public int getHealth() {
        return health;
    }
}
