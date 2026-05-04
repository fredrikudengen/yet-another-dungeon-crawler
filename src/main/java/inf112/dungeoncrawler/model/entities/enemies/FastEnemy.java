package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.entities.Player;

/**
 * A lightweight enemy that moves quickly but deals low damage and has
 * little health. Prioritizes speed over fighting power.
 */
public class FastEnemy extends Enemy {

    public FastEnemy(float x, float y, Player player) {
        super(x, y, player);
        this.damage         = 1;
        this.health         = 2;
        this.speed          = 240;
        this.knockBackForce = 300;
    }
}