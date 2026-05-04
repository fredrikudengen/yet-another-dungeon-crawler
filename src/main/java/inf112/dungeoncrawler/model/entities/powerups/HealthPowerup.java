package inf112.dungeoncrawler.model.entities.powerups;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.sound.SoundService;

/**
 * A one-time pickup that immediately increases the player's health by one.
 * Removed from the world as soon as it is collected, therefore it does not
 * use pickedUp and instead sets isAlive to false immediately.
 */
public class HealthPowerup extends Powerup {

    public HealthPowerup(float x, float y, SoundService sound) {
            super(x, y, sound);
        }

    @Override
    public void step(GameWorld world, double deltaTime) {
        super.step(world, deltaTime);

        if (this.collidesWith(world.getPlayer())) {
            this.onCollision(world.getPlayer());
        }
    }

    @Override
    public void onCollision(Entity entity) {
        entity.setHealth(entity.getHealth() + 1);
        this.isAlive = false;
    }
}
