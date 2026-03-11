package inf112.skeleton.model.entities.powerups;

import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.entities.Player;

public class AttackPowerup extends Powerup {
    private boolean hasDamage;
    protected double attackTimer, currentTime;

    public AttackPowerup(float x, float y) {
        super(x, y);
    }

    @Override
    public String getTextureName() {
        return "src/main/resources/Life/Life.png";
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
        entity.damage += 3;
        this.hasDamage = true;
        System.out.println("Player picked up powerup and got attack++!");
    }

    private void endPowerup(Entity entity) {
        System.out.println("Player got normal attack back!");
        entity.damage -= 3;
    }

    @Override
    public void step(Player player, double deltaTime) {
        currentTime += deltaTime;

        if (this.collidesWith(player)) {
            this.onCollision(player);
            attackTimer = currentTime + 100;
        }

        if (hasDamage) {
            if (currentTime >= attackTimer) {
                this.endPowerup(player);
                hasDamage = false;
            }
        }
    }
}
