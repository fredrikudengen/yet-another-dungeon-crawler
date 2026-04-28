package inf112.dungeoncrawler.model.world;

import inf112.dungeoncrawler.model.generation.SpawnPoint;

import java.util.ArrayList;
import java.util.List;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public class Room {

    private final int width;
    private final int height;
    private final TileType[][] tiles;
    private final List<SpawnPoint> spawnPoints = new ArrayList<>();

    /**
     * Represents a single dungeon room as a 2D grid of TileType tiles,
     * along with a list of spawn points for entities and doors.
     * Tile coordinates use grid units, world coordinates are tile * TILE_SIZE.
     *
     * @param width  room width in tiles
     * @param height room height in tiles
     */
    public Room(int width, int height) {
        this.width  = width;
        this.height = height;
        this.tiles  = new TileType[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = TileType.FLOOR;
            }
        }
    }

    /**
     * Returns the tile type at the given grid coordinates.
     * Returns TileType for out-of-bounds coordinates.
     *
     * @param x grid column
     * @param y grid row
     */
    public TileType getTileAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return TileType.WALL;
        return tiles[x][y];
    }

    /**
     * Sets the tile type at the given grid coordinates. Out-of-bounds coordinates are ignored.
     *
     * @param x    grid column
     * @param y    grid row
     * @param type the tile type to set
     * */
    public void setTileAt(int x, int y, TileType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = type;
        }
    }

    /** Replaces all closed door tiles with their open variants. */
    public void openDoors() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y].isDoorClosed()) {
                    tiles[x][y] = tiles[x][y].toOpenVariant();
                }
            }
        }
    }

    /**
     * Returns true if any of the four corners of the given bounding box overlap
     * a tile that blocks movement, or lie outside the room bounds.
     *
     * @param worldX  left edge of the bounding box in world pixels
     * @param worldY  bottom edge of the bounding box in world pixels
     * @param width   bounding box width in pixels
     * @param height  bounding box height in pixels
     */
    public boolean isBlocked(float worldX, float worldY, float width, float height) {
        float right = worldX + width - 1f;
        float top = worldY + height - 1f;
        return isTileBlocked(worldX, worldY)
                || isTileBlocked(right, worldY)
                || isTileBlocked(worldX, top)
                || isTileBlocked(right, top);
    }

    /**
     * Registers a spawn point in this room.
     *
     * @param spawnPoint the spawn point to add
     */
    public void addSpawnPoint(SpawnPoint spawnPoint) {
        spawnPoints.add(spawnPoint);
    }

    private boolean isTileBlocked(float worldX, float worldY) {
        int gridX = (int) Math.floor(worldX / TILE_SIZE);
        int gridY = (int) Math.floor(worldY / TILE_SIZE);
        if (gridX < 0 || gridY < 0 || gridX >= width || gridY >= height) return true;
        return getTileAt(gridX, gridY).blocksMovement();
    }

    public List<SpawnPoint> getSpawnPoints() { return spawnPoints; }
    public int getWidth()                    { return width;       }
    public int getHeight()                   { return height;      }
    public TileType[][] getTiles()           { return tiles;       }
}