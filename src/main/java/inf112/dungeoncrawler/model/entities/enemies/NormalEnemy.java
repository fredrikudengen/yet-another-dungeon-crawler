package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.entities.Player;

public class NormalEnemy extends Enemy {

    public NormalEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage         = 2;
        this.health         = 2;
        this.speed          = 150;
        this.knockBackForce = 350;
    }

    @Override
    public String getBaseTexturePath() {
        return "enemy/normal/enemy_normal_";
    }
}