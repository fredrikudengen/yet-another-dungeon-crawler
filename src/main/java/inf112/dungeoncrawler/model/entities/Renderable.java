package inf112.dungeoncrawler.model.entities;

public interface Renderable {
    String getBaseTexturePath();

    default boolean isVisible() {
        return true;
    }
}