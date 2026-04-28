package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;

public abstract class Entity implements Collidable, Renderable {
    protected float x, y;
    protected int health, speed, damage;

    protected int width, height;
    protected float velocityX, velocityY;

    protected float knockBackForce;
    private static final float KNOCKBACK_FRICTION = 800f;
    protected float knockbackX = 0f;
    protected float knockbackY = 0f;
    protected boolean isAlive = true;

    /**
     * Base class for all game entities.
     * Handles position, health, velocity, knockback physics, and tile collision.
     *
     * @param x initial x-position in world pixels
     * @param y initial y-position in world pixels
     */
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Updates all entity state for one simulation step.
     * Called each frame by GameWorld.step().
     *
     * @param world     the current game world, used to query room and entities
     * @param deltaTime elapsed time since last frame in seconds
     */
    public abstract void step(GameWorld world, double deltaTime);

    /**
     * Decelerates the knockback velocity each frame using a fixed friction value.
     * Should be called once per frame in step().
     *
     * @param deltaTime elapsed time since last frame in seconds
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

    /**
     * Moves the entity by dx, dy and resolving tile collisions.
     * If the combined move is blocked, tries moving along each axis independently
     * to allow sliding along walls.
     *
     * @param room the room whose tiles define the collision boundaries
     * @param dx   horizontal displacement in pixels
     * @param dy   vertical displacement in pixels
     */
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

    /**
     * Reduces health and applies knockback away from the source.
     * Sets isAlive to false if health drops to zero or below.
     *
     * @param damage  amount of health to remove
     * @param sourceX x-coordinate of the damage source (used to compute knockback direction)
     * @param sourceY y-coordinate of the damage source
     */
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

    /**
     * Sets the knockback velocity directly, overwriting any existing knockback.
     *
     * @param vx horizontal knockback velocity in pixels per second
     * @param vy vertical knockback velocity in pixels per second
     */
    public void applyKnockback(float vx, float vy) {
        this.knockbackX = vx;
        this.knockbackY = vy;
    }

    /** Teleports the entity to the given world-pixel coordinates without collision checking. */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override public float getX()      { return x; }
    @Override public float getY()      { return y; }
    @Override public float getWidth()  { return width; }
    @Override public float getHeight() { return height; }

    public boolean isAlive()  { return this.isAlive; }
    public int getHealth()    { return this.health; }
    public int getSpeed()     { return this.speed; }
    public int getDamage()    { return this.damage; }

    public void setDamage(int damage) { this.damage = damage; }
    public void setHealth(int health) { this.health = health; }
    public void setSpeed(int speed)   { this.speed  = speed; }
}