package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.entities.Player;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public class SlowEnemy extends Enemy {

    public SlowEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage         = 3;
        this.health         = 3;
        this.speed          = 120;
        this.knockBackForce = 400;
    }

    @Override
    public String getBaseTexturePath() {
        return "enemy/slow/enemy_";
    }
}