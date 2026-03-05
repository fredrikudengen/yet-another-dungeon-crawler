package inf112.skeleton.model.entities;

public class FastEnemy extends Enemy {

    public FastEnemy(float x, float y, float width, float height, int speed, Player player) {
        super(x, y, width, height, speed, player);
    }

    @Override
    public String getTextureName() {
        return "blue.jpg";
    }
}
