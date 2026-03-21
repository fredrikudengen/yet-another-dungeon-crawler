package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public class FastEnemy extends Enemy {

    public FastEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage = 1;
        this.width  = TILE_SIZE;
        this.height = TILE_SIZE;
        this.health = 3;
        this.speed  = 240;
        this.knockBackForce = 300;
    }

    @Override
    public void onCollision(Entity entity) {
        entity.takeDamage(damage, x, y);
    }

    @Override
    public String getBaseTexturePath() {
        return "enemy/fast/enemy_";
    }
}