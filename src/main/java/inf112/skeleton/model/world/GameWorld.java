package inf112.skeleton.model.world;

import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.entities.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld implements Iterable<Entity> {
    private Room room;
    private List<Entity> entities;
    private double currentTime;

    public GameWorld(Room room) {
        this.room = room;
        this.entities = new ArrayList<>();
        this.currentTime = 0;
    }

    public void step(double deltaTime) {
        currentTime += deltaTime;

        for (Entity entity : entities) {
            entity.step(this, deltaTime);

            if (entity.getHealth() <= 0) {
                removeEntity(entity);
            }
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Room getRoom() {
        return room;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    @Override
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }
}