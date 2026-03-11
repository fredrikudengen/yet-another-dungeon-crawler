package inf112.skeleton.model.entities;

public class HealthPowerup extends Powerup {

        public HealthPowerup(float x, float y, float width, float height) {
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
        entity.health += 1;
        System.out.print("Player picked up powerup and got health!");
    }
}
