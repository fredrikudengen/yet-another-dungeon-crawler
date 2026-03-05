package inf112.skeleton.model.entities;

import inf112.skeleton.model.world.GameWorld;
import inf112.skeleton.model.world.Room;

public class Enemy extends Entity {

    protected Player player;

    public Enemy(float x, float y, float width, float height, int speed, Player player) {
        super(x, y, width, height, speed);
        this.player = player;
    }

    @Override
    public void step(GameWorld world, double deltaTime) {
        float xNeededToMove = player.getX() - x;
        float yNeededToMove = player.getY() - y;

        velocityX = 0;
        velocityY = 0;
        if (xNeededToMove < 0)  velocityX -= speed;
        if (xNeededToMove > 0) velocityX += speed;
        if (yNeededToMove > 0)    velocityY += speed;
        if (yNeededToMove < 0)  velocityY -= speed;

        float newX = x + velocityX * (float)deltaTime;
        float newY = y + velocityY * (float)deltaTime;

        Room room = world.getRoom();
        int tileSize = 32;

        if (!room.isBlocked(newX, newY, width, height, tileSize)) {
            setPosition(newX, newY);
            return;
        }

        if (!room.isBlocked(newX, y, width, height, tileSize)) {
            setPosition(newX, y);
        }
        if (!room.isBlocked(x, newY, width, height, tileSize)) {
            setPosition(x, newY);
        }
    }

    @Override
    public String getTextureName() {
        return "";
    }
}
