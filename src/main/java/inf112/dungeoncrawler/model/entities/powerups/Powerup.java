package inf112.dungeoncrawler.model.entities.powerups;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public abstract class Powerup extends Entity {

    private final SoundService sound;
    protected boolean pickedUp = false;

    /**
     * Abstract base class for all powerups. Handles visibility, sound on pickup,
     * and tile sizing. Subclasses define the effect applied to the player.
     *
     * @param x     x-position in world pixels
     * @param y     y-position in world pixels
     * @param sound used to play the pickup sound effect
     */
    public Powerup(float x, float y, SoundService sound) {
        super(x, y);
        this.sound  = sound;
        this.width  = TILE_SIZE;
        this.height = TILE_SIZE;
    }

    @Override
    public void step(GameWorld world, double deltaTime) {
        if (!pickedUp && this.collidesWith(world.getPlayer())) {
            sound.play(SoundEffect.POWERUP_PICKUP);
        }
    }

    /** @return true if this powerup has already been collected by the player */
    public boolean isPickedUp() {
        return pickedUp;
    }
}