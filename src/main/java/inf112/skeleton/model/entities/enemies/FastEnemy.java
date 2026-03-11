package inf112.skeleton.model.entities.enemies;

import inf112.skeleton.model.entities.Player;

public class FastEnemy extends Enemy {

    public FastEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage = 1;
        this.width = 16;
        this.height = 16;
        this.health = 1;
        this.speed = 180;
    }

    @Override
    public String getTextureName() {
        return "blue.jpg";
    }
}
