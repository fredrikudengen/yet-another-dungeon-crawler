package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;

public interface Controllable {
    void setMovingLeft(boolean moving);
    void setMovingRight(boolean moving);
    void setMovingUp(boolean moving);
    void setMovingDown(boolean moving);
    void performAttack(Direction direction);
}