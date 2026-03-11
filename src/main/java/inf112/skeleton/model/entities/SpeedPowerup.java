package inf112.skeleton.model.entities;

public class SpeedPowerup extends Powerup {

    private boolean hasSpeed;
    protected double speedTimer, currentTime;

    public SpeedPowerup(float x, float y, float width, float height) {
        super(x, y, width, height);
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
        entity.speed += 100;
        this.hasSpeed = true;
        System.out.println("Player picked up powerup and got speed!");
    }

    @Override
    public void endPowerup(Entity entity) {
        System.out.println("Player got normal speed back!");
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
