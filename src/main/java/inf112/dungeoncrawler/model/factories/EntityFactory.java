package inf112.dungeoncrawler.model.factories;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.enemies.FastEnemy;
import inf112.dungeoncrawler.model.entities.enemies.NormalEnemy;
import inf112.dungeoncrawler.model.entities.enemies.SlowEnemy;
import inf112.dungeoncrawler.model.entities.powerups.AttackPowerup;
import inf112.dungeoncrawler.model.entities.powerups.HealthPowerup;
import inf112.dungeoncrawler.model.entities.powerups.SpeedPowerup;
import inf112.dungeoncrawler.model.generation.SpawnPoint;
import inf112.dungeoncrawler.sound.SoundService;

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

    /**
     * Register each SpawnPoint.Type for all the entities to be used by the EntityFactory.
     *
     * @param player object to be inserted into Enemy types
     * @param sound to be inserted into Powerup types
     * @return the EntityFactory with all SpawnPoint.Type registered
     */
    public static EntityFactory createDefault(Player player, SoundService sound) {
        EntityFactory factory = new EntityFactory();
        factory.register(SpawnPoint.Type.FASTENEMY,     (x, y) -> new FastEnemy(x, y, player));
        factory.register(SpawnPoint.Type.NORMALENEMY,   (x, y) -> new NormalEnemy(x, y, player));
        factory.register(SpawnPoint.Type.SLOWENEMY,     (x, y) -> new SlowEnemy(x, y, player));
        factory.register(SpawnPoint.Type.HEALTHPOWERUP, (x, y) -> new HealthPowerup(x, y, sound));
        factory.register(SpawnPoint.Type.SPEEDPOWERUP,  (x, y) -> new SpeedPowerup(x, y, sound));
        factory.register(SpawnPoint.Type.ATTACKPOWERUP, (x, y) -> new AttackPowerup(x, y, sound));
        return factory;
    }

}