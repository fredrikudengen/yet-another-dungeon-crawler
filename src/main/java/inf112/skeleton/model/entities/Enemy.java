package inf112.skeleton.model.entities;

import inf112.skeleton.model.world.GameWorld;

public class Enemy extends Entity {

    public Enemy(float x, float y, float width, float height, int speed) {
        super(x, y, width, height, speed);
    }

    @Override
    public void step(GameWorld world, double deltaTime) {

    }

    @Override
    public String getTextureName() {
        return "";
    }
}
