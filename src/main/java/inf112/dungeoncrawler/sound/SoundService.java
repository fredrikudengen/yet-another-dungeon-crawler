package inf112.dungeoncrawler.sound;

public interface SoundService {
    void play(SoundEffect effect);
    void dispose();

    default void playMusic()  {}
    default void pauseMusic() {}
    default void stopMusic()  {}
}