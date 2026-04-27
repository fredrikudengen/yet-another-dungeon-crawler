package inf112.dungeoncrawler.model.world;

public enum TileType {
    EMPTY("tiles/empty.png", true),
    FLOOR("tiles/wall2.png", false),
    WALL("tiles/wall3.png", true),
    DOOR_CLOSED("tiles/door_up1.png", true),
    DOOR_OPEN("tiles/door_up2.png", false),
    DOOR_LEFT_WALL_CLOSED("tiles/doorRight1.png", true),
    DOOR_LEFT_WALL_OPEN("tiles/doorRight2.png", false),
    DOOR_RIGHT_WALL_CLOSED("tiles/doorLeft1.png", true),
    DOOR_RIGHT_WALL_OPEN("tiles/doorLeft2.png", false),
    DOOR_TOP_CLOSED("tiles/door_up1.png", true),
    DOOR_TOP_OPEN("tiles/door_up2.png", false),
    DOOR_BOTTOM_CLOSED("tiles/doorDown1.png", true),
    DOOR_BOTTOM_OPEN("tiles/doorDown22.png", false);

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

    public boolean isDoorClosed() {
        return switch (this) {
            case DOOR_CLOSED,
                 DOOR_LEFT_WALL_CLOSED,
                 DOOR_RIGHT_WALL_CLOSED,
                 DOOR_TOP_CLOSED,
                 DOOR_BOTTOM_CLOSED -> true;
            default -> false;
        };
    }

    public boolean isDoorOpen() {
        return switch (this) {
            case DOOR_OPEN,
                 DOOR_LEFT_WALL_OPEN,
                 DOOR_RIGHT_WALL_OPEN,
                 DOOR_TOP_OPEN,
                 DOOR_BOTTOM_OPEN -> true;
            default -> false;
        };
    }

    public TileType toOpenVariant() {
        return switch (this) {
            case DOOR_CLOSED -> DOOR_OPEN;
            case DOOR_LEFT_WALL_CLOSED -> DOOR_LEFT_WALL_OPEN;
            case DOOR_RIGHT_WALL_CLOSED -> DOOR_RIGHT_WALL_OPEN;
            case DOOR_TOP_CLOSED -> DOOR_TOP_OPEN;
            case DOOR_BOTTOM_CLOSED -> DOOR_BOTTOM_OPEN;
            default -> this;
        };
    }
}