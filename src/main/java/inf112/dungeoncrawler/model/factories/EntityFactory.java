package inf112.dungeoncrawler.model.factories;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.generation.SpawnPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry-based factory for spawning Entity instances from
 * SpawnPoint descriptors.
 */
public class EntityFactory {

    /**
     * Functional interface for creating an entity at a given position.
     */
    @FunctionalInterface
    public interface EntitySpawner {

        /**
         * Creates and returns an entity at the given world-pixel coordinates.
         *
         * @param x x-position in world pixels
         * @param y y-position in world pixels
         */
        Entity spawn(float x, float y);
    }

    /**
     * Registers a spawner for the given spawn point type.
     * Overwrites any previously registered spawner for that type.
     *
     * @param type    the spawn point type this spawner handles
     * @param spawner the factory function that produces the entity
     */
    public void register(SpawnPoint.Type type, EntitySpawner spawner) {
        registry.put(type, spawner);
    }

    /**
     * Creates the entity corresponding to the given spawn point.
     *
     * @param spawnPoint the spawn point to instantiate
     * @return the newly created entity
     * @throws IllegalArgumentException if no spawner is registered for the spawn point's type
     */
    public Entity spawn(SpawnPoint spawnPoint) {
        EntitySpawner spawner = registry.get(spawnPoint.getType());
        if (spawner == null) {
            throw new IllegalArgumentException("No spawner registered for type: " + spawnPoint.getType());
        }
        return spawner.spawn(spawnPoint.getX(), spawnPoint.getY());
    }

    private final Map<SpawnPoint.Type, EntitySpawner> registry = new HashMap<>();

}