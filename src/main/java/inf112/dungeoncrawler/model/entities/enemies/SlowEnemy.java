package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;

public class SlowEnemy extends Enemy {

    public SlowEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage = 3;
        this.width = 48;
        this.height = 48;
        this.health = 3;
        this.speed = 75;
    }

    @Override
    public void onCollision(Entity entity) {
        entity.health -= damage;
    }

    @Override
    public String getTextureName() {
        return "blue.jpg";
    }
}
