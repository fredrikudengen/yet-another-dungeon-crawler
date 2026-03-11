package inf112.skeleton.model.entities.powerups;

import inf112.skeleton.model.entities.Collidable;
import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.entities.Player;

public abstract class Powerup implements Collidable {
    protected float x, y, width, height;

    public Powerup(float x, float y) {
        this.x = x;
        this.y = y;
        this.height = 32;
        this.width = 32;
    }

    public abstract String getTextureName();

    public abstract void step(Player player, double deltaTime);
}
