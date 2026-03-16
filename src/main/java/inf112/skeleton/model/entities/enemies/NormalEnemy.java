package inf112.skeleton.model.entities.enemies;

import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.entities.Player;

public class NormalEnemy extends Enemy {

    public NormalEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage = 2;
        this.width  = 32;
        this.height = 32;
        this.health = 2;
        this.speed  = 100;
    }

    @Override
    public void onCollision(Entity entity) {
        entity.health -= damage;
    }

    @Override
    public String getBaseTexturePath() {
        return "src/main/resources/Enemy/Normal";
    }
}