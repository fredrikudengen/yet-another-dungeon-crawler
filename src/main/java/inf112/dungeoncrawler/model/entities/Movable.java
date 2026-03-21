package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;

public interface Movable {
    boolean isMoving();
    Direction getFacingDirection();
}