package inf112.skeleton.model.entities;

public class FastEnemy extends Enemy {

    public FastEnemy(float x, float y, float width, float height, int speed, int health, Player player) {
        super(x, y, width, height, speed, health, player);
        this.damage = 1;
    }

    @Override
    public String getTextureName() {
        return "blue.jpg";
    }
}
