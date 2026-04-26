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

    public TileType getTileAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return TileType.WALL;
        return tiles[x][y];
    }

    public void setTileAt(int x, int y, TileType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = type;
        }
    }

    public void openDoors() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y].isDoorClosed()) {
                    tiles[x][y] = tiles[x][y].toOpenVariant();
                }
            }
        }
    }

    public boolean isBlocked(float worldX, float worldY, float width, float height) {
        float right = worldX + width - 1f;
        float top = worldY + height - 1f;
        return isTileBlocked(worldX, worldY)
                || isTileBlocked(right, worldY)
                || isTileBlocked(worldX, top)
                || isTileBlocked(right, top);
    }

    private boolean isTileBlocked(float worldX, float worldY) {
        int gridX = (int) Math.floor(worldX / TILE_SIZE);
        int gridY = (int) Math.floor(worldY / TILE_SIZE);
        if (gridX < 0 || gridY < 0 || gridX >= width || gridY >= height) return true;
        return getTileAt(gridX, gridY).blocksMovement();
    }

    public void addSpawnPoint(SpawnPoint spawnPoint) {
        spawnPoints.add(spawnPoint);
    }

    public List<SpawnPoint> getSpawnPoints() { return spawnPoints; }
    public int getWidth()                    { return width;       }
    public int getHeight()                   { return height;      }
    public TileType[][] getTiles()           { return tiles;       }
}