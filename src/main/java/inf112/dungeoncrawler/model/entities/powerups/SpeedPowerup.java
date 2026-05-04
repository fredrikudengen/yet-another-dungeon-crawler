package inf112.dungeoncrawler.model.entities.powerups;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.sound.SoundService;

/**
 * A timed pickup that increases the player's movement speed for ten seconds,
 * then restores it to its original value before removing itself from the world.
 */
public class SpeedPowerup extends Powerup {

    private double speedTimer;
    private double currentTime;

    public SpeedPowerup(float x, float y, SoundService sound) {
        super(x, y, sound);
    }

    @Override
    public void step(GameWorld world, double deltaTime) {
        if (!pickedUp) {
            super.step(world, deltaTime);
            if (this.collidesWith(world.getPlayer())) {
                onCollision(world.getPlayer());
                pickedUp   = true;
                speedTimer = currentTime + 10;
            }
        }

        currentTime += deltaTime;

        if (pickedUp && currentTime >= speedTimer) {
            endPowerup(world.getPlayer());
            isAlive = false;
        }
    }

    @Override
    public void onCollision(Entity entity) {
        entity.setSpeed(entity.getSpeed() + 100);
    }

    private void endPowerup(Entity entity) {
        entity.setSpeed(entity.getSpeed() - 100);
    }
}