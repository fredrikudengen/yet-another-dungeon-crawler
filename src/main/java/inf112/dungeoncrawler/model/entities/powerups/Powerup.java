package inf112.dungeoncrawler.model.entities.powerups;

import inf112.dungeoncrawler.model.entities.Collidable;
import inf112.dungeoncrawler.model.entities.Player;
import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public abstract class Powerup implements Collidable {
    protected float x, y, width, height;

    public Powerup(float x, float y) {
        this.x = x;
        this.y = y;
        this.height = TILE_SIZE;
        this.width = TILE_SIZE;
    }

    public abstract String getTextureName();

    public abstract void step(Player player, double deltaTime);
}
