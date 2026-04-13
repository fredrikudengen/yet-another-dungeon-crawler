package inf112.dungeoncrawler.model.factories;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.generation.SpawnPoint;

import java.util.HashMap;
import java.util.Map;

public class EntityFactory {

    @FunctionalInterface
    public interface EntitySpawner {
        Entity spawn(float x, float y);
    }

    private final Map<SpawnPoint.Type, EntitySpawner> registry = new HashMap<>();

    public void register(SpawnPoint.Type type, EntitySpawner spawner) {
        registry.put(type, spawner);
    }

    public Entity spawn(SpawnPoint spawnPoint) {
        EntitySpawner spawner = registry.get(spawnPoint.type);
        if (spawner == null) {
            throw new IllegalArgumentException("No spawner registered for type: " + spawnPoint.type);
        }
        return spawner.spawn(spawnPoint.x, spawnPoint.y);
    }
}