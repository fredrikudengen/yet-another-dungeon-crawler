package inf112.dungeoncrawler.model.entities;

/**
 * Represents an entity that can be drawn on screen.
 * Implemented by all Entity subclasses.
 */
public interface Renderable {

    /**
     * Returns the base path used to construct the entity's texture filename.
     * The AnimationController appends the direction and frame index to this path.
     *
     * @return base asset path (e.g. "enemy/fast/enemy_")
     */
    String getBaseTexturePath();

    /**
     * Returns whether this entity should be rendered.
     * Defaults to true; override to hide the entity
     */
    default boolean isVisible() { return true; }
}