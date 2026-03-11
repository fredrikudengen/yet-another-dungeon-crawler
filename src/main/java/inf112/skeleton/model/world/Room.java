package inf112.skeleton.model.world;

import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.generation.SpawnPoint;

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

    public boolean isBlocked(double worldX, double worldY, float width, float height, int tileSize) {
        return isTileBlocked(worldX, worldY, tileSize) || // nedre venstre
                isTileBlocked(worldX + width, worldY, tileSize) || // nedre høyre
                isTileBlocked(worldX,worldY + height, tileSize) || // øvre venstre
                isTileBlocked(worldX + width,worldY + height, tileSize);   // øvre høyre
    }

    private boolean isTileBlocked(double worldX, double worldY, int tileSize) {
        int gridX = (int) (worldX / tileSize);
        int gridY = (int) (worldY / tileSize);

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