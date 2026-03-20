package inf112.dungeoncrawler.model.world;

public enum TileType {
    EMPTY("tiles/empty.png", true),
    FLOOR("tiles/wall2.png", false),
    WALL("tiles/wall3.png", true);

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