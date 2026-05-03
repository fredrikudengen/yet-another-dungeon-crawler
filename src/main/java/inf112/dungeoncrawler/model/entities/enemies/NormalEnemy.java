package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.entities.Player;

/**
 * A balanced enemy type with moderate speed, health, and damage.
 * Intended as the standard threat the player will encounter most often.
 */
public class NormalEnemy extends Enemy {

    public NormalEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage         = 2;
        this.health         = 3;
        this.speed          = 150;
        this.knockBackForce = 350;
    }

    @Override
    public String getBaseTexturePath() {
        return "enemy/normal/enemy_normal_";
    }
}