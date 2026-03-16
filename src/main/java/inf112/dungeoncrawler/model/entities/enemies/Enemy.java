package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;

public abstract class Enemy extends Entity {

    protected Player player;

    public Enemy(float x, float y, Player player) {
        super(x, y);
        this.player = player;
    }

    @Override
    public void step(GameWorld world, double deltaTime) {
        if (this.collidesWith(player)) {
            this.onCollision(player);
        }

        float dx = player.getX() - x;
        float dy = player.getY() - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            velocityX = (dx / distance) * speed;
            velocityY = (dy / distance) * speed;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        float newX = x + velocityX * (float) deltaTime;
        float newY = y + velocityY * (float) deltaTime;

        // TODO: implement smarter enemy AI to walk around obstacles

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
    public abstract void onCollision(Entity entity);

    public abstract String getBaseTexturePath();

    public Direction getFacingDirection() {
        if (Math.abs(velocityX) >= Math.abs(velocityY)) {
            return (velocityX >= 0) ? Direction.RIGHT : Direction.LEFT;
        }
        return (velocityY >= 0) ? Direction.UP : Direction.DOWN;
    }

    public boolean isMoving() {
        return velocityX != 0 || velocityY != 0;
    }
}