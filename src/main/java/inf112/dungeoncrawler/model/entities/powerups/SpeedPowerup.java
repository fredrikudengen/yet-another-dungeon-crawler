package inf112.dungeoncrawler.model.entities.powerups;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;

public class SpeedPowerup extends Powerup {

    private boolean hasSpeed;
    protected double speedTimer, currentTime;

    public SpeedPowerup(float x, float y) {
        super(x, y);
    }

    @Override
    public String getTextureName() {
        return "src/main/resources/powerups/Life.png";
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void onCollision(Entity entity) {
        entity.speed += 100;
        this.hasSpeed = true;
    }

    private void endPowerup(Entity entity) {
        entity.speed -= 100;
    }

    @Override
    public void step(Player player, double deltaTime) {
        currentTime += deltaTime;

        if (this.collidesWith(player)) {
            this.onCollision(player);
            speedTimer = currentTime + 100;
        }

        if (hasSpeed) {
            if (currentTime >= speedTimer) {
                this.endPowerup(player);
                hasSpeed = false;
            }
        }
    }
}
