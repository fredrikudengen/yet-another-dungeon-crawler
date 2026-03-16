package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;

public class FastEnemy extends Enemy {

    public FastEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage = 1;
        this.width  = 32;
        this.height = 32;
        this.health = 1;
        this.speed  = 180;
    }

    @Override
    public void onCollision(Entity entity) {
        entity.health -= damage;
    }

    @Override
    public String getBaseTexturePath() {
        return "src/main/resources/Enemy/Fast";
    }
}