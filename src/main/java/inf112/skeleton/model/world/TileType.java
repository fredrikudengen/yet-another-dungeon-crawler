package inf112.skeleton.model.world;

public enum TileType {
    FLOOR("floor2.png", false),
    WALL("wall2.png", true);

    private final String textureName;
    private final boolean blocksMovement;

    TileType(String textureName, boolean blocksMovement) {
        this.textureName = textureName;
        this.blocksMovement = blocksMovement;
    }

    public String getTextureName() {
        return textureName;
    }

    public boolean blocksMovement() {
        return blocksMovement;
    }
}