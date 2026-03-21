package inf112.dungeoncrawler.model.entities.powerups;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.sound.SoundPlayer;
import inf112.dungeoncrawler.sound.SoundService;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public abstract class Powerup extends Entity {

    private SoundService sound;

    protected boolean pickedUp = false;

    public Powerup(float x, float y, SoundService sound) {
        super(x, y);
        this.sound = sound;
        this.width  = TILE_SIZE;
        this.height = TILE_SIZE;
    }
    @Override
    public void step(GameWorld world, double deltaTime) {
        if (this.collidesWith(world.getPlayer())) {
            sound.play(SoundPlayer.SoundEffect.POWERUP_PICKUP);
        }
    }

    @Override
    public String getBaseTexturePath() { return null; }

    public boolean isPickedUp() {
        return this.pickedUp;
    }
}