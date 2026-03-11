package inf112.skeleton.model.generation;

public class SpawnPoint {

    public enum Type { ENEMY, PLAYER }

    public final int x;
    public final int y;
    public final Type type;

    public SpawnPoint(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}