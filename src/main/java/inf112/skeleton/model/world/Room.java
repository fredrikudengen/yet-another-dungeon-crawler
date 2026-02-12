package inf112.skeleton.model.world;

import inf112.skeleton.model.entities.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single room in the dungeon with a grid-based tile layout.
 */
public class Room {
    private final int width;
    private final int height;
    private final TileType[][] tiles;
    private final List<Entity> entities;

    /**
     * Creates a new room with the specified dimensions.
     * All tiles are initialized to FLOOR by default.
     *
     * @param width Width of the room in tiles
     * @param height Height of the room in tiles
     */
    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new TileType[width][height];
        this.entities = new ArrayList<>();

        // Initialize all tiles to FLOOR
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = TileType.FLOOR;
            }
        }
    }

    /**
     * Gets the tile type at the specified grid coordinates.
     * If coordinates are out of bounds, returns WALL.
     *
     * @param x X coordinate in the tile grid
     * @param y Y coordinate in the tile grid
     * @return The tile type at that position, or WALL if out of bounds
     */
    public TileType getTileAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return TileType.WALL; // Out of bounds treated as wall
        }
        return tiles[x][y];
    }

    /**
     * Sets the tile type at the specified grid coordinates.
     * Does nothing if coordinates are out of bounds.
     *
     * @param x X coordinate in the tile grid
     * @param y Y coordinate in the tile grid
     * @param type The tile type to set
     */
    public void setTileAt(int x, int y, TileType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = type;
        }
    }

    /**
     * Checks if a position in world coordinates would collide with a solid tile.
     *
     * @param worldX X position in world coordinates (pixels)
     * @param worldY Y position in world coordinates (pixels)
     * @param tileSize Size of each tile in pixels
     * @return True if the position is blocked by a wall or other solid tile
     */
    public boolean isBlocked(double worldX, double worldY, int tileSize) {
        int gridX = (int) (worldX / tileSize);
        int gridY = (int) (worldY / tileSize);
        return getTileAt(gridX, gridY).blocksMovement();
    }

    /**
     * Fills the entire room with a specific tile type.
     *
     * @param type The tile type to fill the room with
     */
    public void fillWith(TileType type) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = type;
            }
        }
    }

    /**
     * Creates walls around the perimeter of the room.
     */
    public void createPerimeterWalls() {
        for (int x = 0; x < width; x++) {
            tiles[x][0] = TileType.WALL;           // Bottom wall
            tiles[x][height - 1] = TileType.WALL;  // Top wall
        }
        for (int y = 0; y < height; y++) {
            tiles[0][y] = TileType.WALL;           // Left wall
            tiles[width - 1][y] = TileType.WALL;   // Right wall
        }
    }

    /**
     * Adds an entity to this room.
     *
     * @param entity The entity to add
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * @return The list of entities currently in this room
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * @return Width of the room in tiles
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Height of the room in tiles
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return The 2D array of tiles (for rendering)
     */
    public TileType[][] getTiles() {
        return tiles;
    }
}