package inf112.dungeoncrawler.model.generation;

/**
 * A descriptor for a location in a room where an entity
 * should be spawned. Created by RoomTemplate when parsing room files.
 */
public class SpawnPoint {

    /**
     * The kind of entities that can be spawned at a point.
     */
    public enum Type {
        FASTENEMY,
        NORMALENEMY,
        SLOWENEMY,
        SPEEDPOWERUP,
        ATTACKPOWERUP,
        HEALTHPOWERUP,
        PLAYER,
        DOOR
    }

    private final int x;
    private final int y;
    private final Type type;

    /**
     * @param x    x-coordinate in world pixels
     * @param y    y-coordinate in world pixels
     * @param type the entity type to spawn at this location
     */
    public SpawnPoint(int x, int y, Type type) {
        this.x    = x;
        this.y    = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }
}