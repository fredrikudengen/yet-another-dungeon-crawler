package inf112.dungeoncrawler.model.world;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.generation.SpawnPoint;
import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final int width;
    private final int height;
    private final TileType[][] tiles;
    private final List<Entity> entities;
    private final List<SpawnPoint> spawnPoints = new ArrayList<>();

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new TileType[width][height];
        this.entities = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = TileType.FLOOR;
            }
        }
    }

    public TileType getTileAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return TileType.WALL;
        }
        return tiles[x][y];
    }

    public void setTileAt(int x, int y, TileType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y] = type;
        }
    }

    public boolean isBlocked(double worldX, double worldY, float width, float height) {
        return isTileBlocked(worldX, worldY) ||
                isTileBlocked(worldX + width, worldY) ||
                isTileBlocked(worldX,worldY + height) ||
                isTileBlocked(worldX + width,worldY + height);
    }

    private boolean isTileBlocked(double worldX, double worldY) {
        int gridX = (int) (worldX / TILE_SIZE);
        int gridY = (int) (worldY / TILE_SIZE);

        if (gridX < 0 || gridY < 0 || gridX >= width || gridY >= height) {
            return true;
        }
        return getTileAt(gridX, gridY).blocksMovement();
    }

    public void addSpawnPoint(SpawnPoint spawnPoint) {
        spawnPoints.add(spawnPoint);
    }

    public List<SpawnPoint> getSpawnPoints() {
        return spawnPoints;
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