package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;

public abstract class Entity implements Collidable {
    protected float x, y;
    protected int health, speed, damage;

    protected int width, height;
    protected float velocityX, velocityY;

    protected float knockBackForce;
    private static final float KNOCKBACK_FRICTION = 800f;
    protected float knockbackX = 0f;
    protected float knockbackY = 0f;
    protected boolean isAlive = true;

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

    public boolean isAlive() { return this.isAlive; }

    public int getHealth() {
        return this.health;
    }

    public int getSpeed() {
        return this.speed;
    }

    public float getDamage() {
        return this.damage;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void applyKnockback(float vx, float vy) {
        this.knockbackX = vx;
        this.knockbackY = vy;
    }

    /**
     * Decelerates knockback velocity each frame using friction.
     * Call once per step before applying knockback movement.
     */
    protected void updateKnockback(float deltaTime) {
        if (knockbackX == 0 && knockbackY == 0) return;
        float kbSpeed   = (float) Math.sqrt(knockbackX * knockbackX + knockbackY * knockbackY);
        float reduction = KNOCKBACK_FRICTION * deltaTime;
        if (reduction >= kbSpeed) {
            knockbackX = 0;
            knockbackY = 0;
        } else {
            float factor = (kbSpeed - reduction) / kbSpeed;
            knockbackX *= factor;
            knockbackY *= factor;
        }
    }

    protected void moveWithCollision(Room room, float dx, float dy) {
        float newX = x + dx;
        float newY = y + dy;
        if (!room.isBlocked(newX, newY, width, height)) {
            setPosition(newX, newY);
        } else if (!room.isBlocked(newX, y, width, height)) {
            setPosition(newX, y);
        } else if (!room.isBlocked(x, newY, width, height)) {
            setPosition(x, newY);
        }
    }

    public void takeDamage(int damage, float sourceX, float sourceY) {
        health -= damage;
        if (health <= 0) {
            isAlive = false;
            return;
        }
        float dx   = x - sourceX;
        float dy   = y - sourceY;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        if (dist > 0) {
            float force = this.knockBackForce;
            applyKnockback((dx / dist) * force, (dy / dist) * force);
        }
    }

    public abstract void step(GameWorld world, double deltaTime);

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract String getBaseTexturePath();

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}