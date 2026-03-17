package inf112.dungeoncrawler.model.world;

public enum TileType {
    EMPTY("Floor_wall_door/empty.png", true),
    FLOOR("Floor_wall_door/floor2.png", false),
    WALL("Floor_wall_door/wall2.png", true);

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