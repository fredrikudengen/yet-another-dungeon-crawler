package inf112.dungeoncrawler.sound;

/**
 * Abstraction over the audio system. Implementations handle playback
 * of sound effects and background music.
 */
public interface SoundService {

    /**
     * Plays the given sound effect once.
     *
     * @param effect the effect to play
     */
    void play(SoundEffect effect);

    /**
     * Releases all audio resources held by this service.
     * Must be called when the application shuts down.
     */
    void dispose();

    /** Starts or resumes background music playback. */
    default void playMusic()  {}

    /** Pauses background music without resetting playback position. */
    default void pauseMusic() {}

    /** Stops background music and resets playback position to the start. */
    default void stopMusic()  {}
}