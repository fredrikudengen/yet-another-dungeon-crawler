package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.entities.Player;

public class FastEnemy extends Enemy {

    public FastEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage         = 1;
        this.health         = 3;
        this.speed          = 240;
        this.knockBackForce = 300;
    }

    @Override
    public String getBaseTexturePath() {
        return "enemy/fast/enemy_";
    }
}