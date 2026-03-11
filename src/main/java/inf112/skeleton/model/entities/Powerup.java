package inf112.skeleton.model.entities;

public abstract class Powerup implements Collidable{
    protected float x, y, width, height;

    public Powerup(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract String getTextureName();

    public void step(Player player) {
        if (this.collidesWith(player)) {
            this.onCollision(player);
        }
    }
}
