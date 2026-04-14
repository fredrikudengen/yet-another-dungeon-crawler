package inf112.dungeoncrawler.model.entities.powerups;

import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public abstract class Powerup extends inf112.dungeoncrawler.model.entities.Entity {

    private final SoundService sound;
    protected boolean pickedUp = false;

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

    public boolean isPickedUp() {
        return pickedUp;
    }

    @Override
    public abstract String getBaseTexturePath();
}