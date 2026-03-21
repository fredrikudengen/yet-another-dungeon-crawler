package inf112.dungeoncrawler.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundPlayer implements SoundService {

    public enum SoundEffect {
        DAMAGE_TAKEN,
        POWERUP_PICKUP,
        SWORD_SWING
    }

    private static SoundPlayer instance;

    private final Sound damageTaken;
    private final Sound powerupPickup;
    private final Sound swordSwing;
    private final Music soundtrack;

    private float masterVolume = 1.0f;
    private float musicVolume  = 0.5f;
    private boolean muted = false;

    private SoundPlayer() {
        damageTaken   = Gdx.audio.newSound(Gdx.files.internal("sound/damageTaken.ogg"));
        powerupPickup = Gdx.audio.newSound(Gdx.files.internal("sound/powerupPickUp.ogg"));
        swordSwing    = Gdx.audio.newSound(Gdx.files.internal("sound/swordSwing.ogg"));

        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("sound/soundtrack.ogg"));
        soundtrack.setLooping(true);
        soundtrack.setVolume(musicVolume);
    }

    public static SoundPlayer getInstance() {
        if (instance == null) {
            instance = new SoundPlayer();
        }
        return instance;
    }

    public void play(SoundEffect effect) {
        if (muted) return;
        switch (effect) {
            case DAMAGE_TAKEN   -> damageTaken.play(masterVolume);
            case POWERUP_PICKUP -> powerupPickup.play(masterVolume);
            case SWORD_SWING    -> swordSwing.play(masterVolume);
        }
    }

    public void playMusic() {
        if (!muted) soundtrack.play();
    }

    public void pauseMusic() {
        soundtrack.pause();
    }

    public void stopMusic() {
        soundtrack.stop();
    }

    public void setMasterVolume(float volume) {
        this.masterVolume = Math.clamp(volume, 0f, 1f);
    }

    public void setMusicVolume(float volume) {
        this.musicVolume = Math.clamp(volume, 0f, 1f);
        soundtrack.setVolume(this.musicVolume);
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
        soundtrack.setVolume(muted ? 0f : musicVolume);
    }

    public boolean isMuted() { return muted; }

    public void dispose() {
        damageTaken.dispose();
        powerupPickup.dispose();
        swordSwing.dispose();
        soundtrack.dispose();
        instance = null;
    }
}