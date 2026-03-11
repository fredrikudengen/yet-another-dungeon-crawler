package inf112.skeleton.model.entities;

public class SpeedPowerup extends Powerup {

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
    public void endSpeed(Entity entity) {
        System.out.println("Player got normal speed back!");
        entity.speed -= 100;
    }
}
