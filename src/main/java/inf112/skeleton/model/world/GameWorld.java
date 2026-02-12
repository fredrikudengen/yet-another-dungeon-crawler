package inf112.skeleton.model.world;

import inf112.skeleton.model.entities.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld implements Iterable<Entity> {
    double width;
    double height;
    double currentTime = 0;
    List<Entity> entities = new ArrayList<>();

    public GameWorld(double width, double height) {
        super();
        this.width = width;
        this.height = height;
    }

    @Override
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }

    public void step(double deltaTime, String direction) {
        currentTime += deltaTime;
        for (Entity entity : entities) {
            entity.step(this, deltaTime);
        }
    }

    public double width() {
        return width;
    }

    public void add(Entity entity) {
        entities.add(entity);
    }
}
