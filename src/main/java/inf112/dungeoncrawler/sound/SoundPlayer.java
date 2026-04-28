package inf112.dungeoncrawler.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundPlayer implements SoundService {

    private final Sound damageTaken;
    private final Sound powerupPickup;
    private final Sound swordSwing;
    private final Music soundtrack;

    private final boolean muted = false;

    /**
     * Concrete SoundService that loads and plays audio via libGDX.
     * Loads all audio assets from the internal asset directory on construction.
     */
    public SoundPlayer() {
        damageTaken   = Gdx.audio.newSound(Gdx.files.internal("sound/damageTaken.ogg"));
        powerupPickup = Gdx.audio.newSound(Gdx.files.internal("sound/powerupPickUp.ogg"));
        swordSwing    = Gdx.audio.newSound(Gdx.files.internal("sound/swordSwing.ogg"));

        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("sound/soundtrack.ogg"));
        soundtrack.setLooping(true);
        float musicVolume = 0.5f;
        soundtrack.setVolume(musicVolume);
    }

    @Override
    public void play(SoundEffect effect) {
        if (muted) return;
        float masterVolume = 1.0f;
        switch (effect) {
            case DAMAGE_TAKEN   -> damageTaken.play(masterVolume);
            case POWERUP_PICKUP -> powerupPickup.play(masterVolume);
            case SWORD_SWING    -> swordSwing.play(masterVolume);
        }
    }

    @Override
    public void playMusic() {
        if (!muted) soundtrack.play();
    }

    @Override
    public void pauseMusic() {
        soundtrack.pause();
    }

    @Override
    public void stopMusic() {
        soundtrack.stop();
    }

    @Override
    public void dispose() {
        damageTaken.dispose();
        powerupPickup.dispose();
        swordSwing.dispose();
        soundtrack.dispose();
    }
}