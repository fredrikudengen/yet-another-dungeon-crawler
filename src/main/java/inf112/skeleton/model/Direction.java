package inf112.skeleton.model;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public boolean isVertical() {
        return this == UP || this == DOWN;
    }

    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }
}
