package inf112.dungeoncrawler.sound;

public interface SoundService {
    void play(SoundPlayer.SoundEffect effect);

    default void playMusic() {};
}