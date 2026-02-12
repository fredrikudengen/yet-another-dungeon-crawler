package inf112.skeleton.model.entities;

import inf112.skeleton.model.world.GameWorld;

public class Player extends Entity {
    private boolean movingLeft, movingRight, movingUp, movingDown;

    public Player(float x, float y) {
        super(x, y, 32, 32, 200);
    }

    @Override
    public void step(GameWorld world, double deltaTime) {
        velocityX = 0;
        velocityY = 0;
        if (movingLeft)  velocityX -= speed;
        if (movingRight) velocityX += speed;
        if (movingUp)    velocityY += speed;
        if (movingDown)  velocityY -= speed;

        updatePosition(deltaTime);
    }

    @Override
    public String getTextureName() {
        return "obligator.png";
    }

    public boolean isMovingLeft() { return movingLeft; }
    public boolean isMovingRight() { return movingRight; }
    public boolean isMovingUp() { return movingUp; }
    public boolean isMovingDown() { return movingDown; }

    public void setMovingLeft(boolean moving) { movingLeft = moving; }
    public void setMovingRight(boolean moving) { movingRight = moving; }
    public void setMovingUp(boolean moving) { movingUp = moving; }
    public void setMovingDown(boolean moving) { movingDown = moving; }

    @Override
    public boolean collidesWith(Collidable other) {
        return super.collidesWith(other);
    }
}
