package inf112.dungeoncrawler.model.world;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.enemies.Enemy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld implements Iterable<Entity> {

    private final Room room;
    private final List<Entity> entities;
    private final Player player;
    private int enemiesKilled = 0;

    /**
     * The central model object for a single room's game state.
     * Holds all active entities, processes each step,
     * and tracks how many enemies have been defeated.
     *
     * @param room   the room this world takes place in
     * @param player the player entity
     */
    public GameWorld(Room room, Player player) {
        this.room = room;
        this.player = player;
        this.entities = new ArrayList<>();
    }

    /**
     * Advances the simulation by one frame. Steps all entities, then removes
     * dead entities and increments the kill counter for each defeated enemy.
     *
     * @param deltaTime elapsed time since last frame in seconds
     */
    public void step(double deltaTime) {
        for (Entity entity : entities) {
            entity.step(this, deltaTime);
        }

        entities.removeIf(entity -> {
            if (!entity.isAlive()) {
                if (entity instanceof Enemy) {
                    enemiesKilled++;
                }
                return true;
            }
            return false;
        });
    }

    // TODO: kanskje endre disse to metodene?
    /** @return true if at least one Enemy entity is still alive */
    public boolean hasEnemiesRemaining() {
        return entities.stream().anyMatch(e -> e instanceof Enemy);
    }

    /** @return the number of living Enemy entities currently in the world */
    public int getEnemiesRemainingCount() {
        return (int) entities.stream().filter(e -> e instanceof Enemy).count();
    }

    /** Opens all doors in the room. Call when all enemies in the room are defeated. */
    public void openDoors() {
        room.openDoors();
    }

    @Override
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }

    /** @return the room instance */
    public Room getRoom() {
        return room;
    }

    /** @return a live list of all entities currently in the world, including the player */
    public List<Entity> getEntities() {
        return entities;
    }

    /** @return the player entity */
    public Player getPlayer() {
        return player;
    }

    /** @return the number of enemies killed since this world was created */
    public int getKilledEnemies() {
        return enemiesKilled;
    }

    /**
     * Adds an entity to the world. The entity will be stepped each frame
     * and removed automatically when Entity.isAlive() returns false.
     *
     * @param entity the entity to add
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}