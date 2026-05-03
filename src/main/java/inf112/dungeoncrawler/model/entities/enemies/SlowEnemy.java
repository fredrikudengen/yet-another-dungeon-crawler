package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.entities.Player;

/**
 * A heavy enemy that moves slowly but hits hard and can take significant
 * damage before dying. Acts as a tank-type threat.
 */
public class SlowEnemy extends Enemy {

    public SlowEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage         = 3;
        this.health         = 4;
        this.speed          = 120;
        this.knockBackForce = 400;
    }

    @Override
    public String getBaseTexturePath() {
        return "enemy/slow/enemy_slow_";
    }
}