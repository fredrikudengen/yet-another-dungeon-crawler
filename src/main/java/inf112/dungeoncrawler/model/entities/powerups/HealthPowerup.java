package inf112.dungeoncrawler.model.entities.powerups;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;

public class HealthPowerup extends Powerup {

    public HealthPowerup(float x, float y) {
            super(x, y);
        }

    @Override
    public String getTextureName() {
            return "src/main/resources/Life/Life.png";
        }

    @Override
    public void step(Player player, double deltaTime) {
        if (this.collidesWith(player)) {
            this.onCollision(player);
        }
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
