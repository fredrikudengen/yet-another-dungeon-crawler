package inf112.skeleton.model.entities;

public abstract class Powerup implements Collidable{
    protected float x, y, width, height;
    protected double speedTimer, currentTime;
    protected boolean hasSpeed;


    public Powerup(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hasSpeed = false;
        this.currentTime = 0;
    }

    public abstract String getTextureName();

    public abstract void endSpeed(Entity entity);

    public void step(Player player, double deltaTime) {
        currentTime += deltaTime;

        if (this.collidesWith(player)) {
            this.onCollision(player);
            speedTimer = currentTime + 100;
        }

        if (hasSpeed) {
            if (currentTime >= speedTimer) {
                this.endSpeed(player);
                hasSpeed = false;
            }
        }
    }
}
