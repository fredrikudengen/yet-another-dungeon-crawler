package inf112.dungeoncrawler.model.entities.powerups;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.sound.SoundService;

/**
 * A timed pickup that increases the player's attack damage by three for ten
 * seconds, then restores it to its original value before removing itself
 * from the world.
 */
public class AttackPowerup extends Powerup {

    private double attackTimer;
    private double currentTime;

    public AttackPowerup(float x, float y, SoundService sound) {
        super(x, y, sound);
    }

    @Override
    public void step(GameWorld world, double deltaTime) {
        if (!pickedUp) {
            super.step(world, deltaTime);
            if (this.collidesWith(world.getPlayer())) {
                onCollision(world.getPlayer());
                pickedUp    = true;
                attackTimer = currentTime + 10;
            }
        }

        currentTime += deltaTime;

        if (pickedUp && currentTime >= attackTimer) {
            endPowerup(world.getPlayer());
            isAlive = false;
        }
    }

    @Override
    public void onCollision(Entity entity) {
        entity.setDamage(entity.getDamage() + 3);
    }

    private void endPowerup(Entity entity) {
        entity.setDamage(entity.getDamage() - 3);
    }
}