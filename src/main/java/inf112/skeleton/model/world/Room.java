package inf112.skeleton.model.world;

import inf112.skeleton.model.entities.Entity;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private final int width;
    private final int height;
    private final TileType[][] tiles;
    private final List<Entity> entities;

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

    public TileType getTileAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return TileType.WALL; // Out of bounds treated as wall
        }
        return tiles[x][y];
    }

    public void setTileAt(int x, int y, TileType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = type;
        }
    }

    public boolean isBlocked(double worldX, double worldY, int tileSize) {
        int gridX = (int) (worldX / tileSize);
        int gridY = (int) (worldY / tileSize);
        return getTileAt(gridX, gridY).blocksMovement();
    }

    public void fillWith(TileType type) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = type;
            }
        }
    }

    public void createPerimeterWalls() {
        for (int x = 0; x < width; x++) {
            tiles[x][0] = TileType.WALL;
            tiles[x][height - 1] = TileType.WALL;
        }
        for (int y = 0; y < height; y++) {
            tiles[0][y] = TileType.WALL;
            tiles[width - 1][y] = TileType.WALL;
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TileType[][] getTiles() {
        return tiles;
    }
}