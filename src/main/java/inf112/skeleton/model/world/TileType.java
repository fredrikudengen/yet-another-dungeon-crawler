package inf112.skeleton.model.world;

/**
 * Enum representing different types of tiles in the game world.
 * Each tile type has properties like texture name and whether it blocks movement.
 */
public enum TileType {
    FLOOR("floor.png", false),
    WALL("wall.png", true);

    private final String textureName;
    private final boolean blocksMovement;

    /**
     * Constructor for TileType enum.
     * @param textureName The name of the texture file for this tile
     * @param blocksMovement Whether entities can walk through this tile
     */
    TileType(String textureName, boolean blocksMovement) {
        this.textureName = textureName;
        this.blocksMovement = blocksMovement;
    }

    /**
     * @return The texture file name for this tile type
     */
    public String getTextureName() {
        return textureName;
    }

    /**
     * @return True if entities cannot pass through this tile
     */
    public boolean blocksMovement() {
        return blocksMovement;
    }

    /**
     * @return True if entities can walk on this tile
     */
    public boolean isWalkable() {
        return !blocksMovement;
    }
}