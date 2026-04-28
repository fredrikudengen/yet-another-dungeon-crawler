package inf112.dungeoncrawler.model.factories;

import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.enemies.FastEnemy;
import inf112.dungeoncrawler.model.entities.enemies.NormalEnemy;
import inf112.dungeoncrawler.model.entities.enemies.SlowEnemy;
import inf112.dungeoncrawler.model.entities.powerups.AttackPowerup;
import inf112.dungeoncrawler.model.entities.powerups.HealthPowerup;
import inf112.dungeoncrawler.model.entities.powerups.SpeedPowerup;
import inf112.dungeoncrawler.model.generation.SpawnPoint;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.sound.SoundService;

import java.util.Objects;

public class GameWorldBuilder {

    private final Room room;
    private final SoundService sound;

    /**
     * Builds a GameWorld from a Room by spawning entities
     * at their designated spawn points.
     *
     * @param room  the room to build the world in
     * @param sound used to inject audio into entities that produce sound
     */
    public GameWorldBuilder(Room room, SoundService sound) {
        this.room  = Objects.requireNonNull(room,  "room must not be null");
        this.sound = Objects.requireNonNull(sound, "sound must not be null");
    }

    /**
     * Creates a new player from the room's player spawn point, spawns all
     * other entities, and returns the fully initialized world.
     *
     * @return a GameWorld
     */
    public GameWorld build() {
        Player player = createPlayer();
        GameWorld world = new GameWorld(room, player);
        world.addEntity(player);
        spawnEntities(world, player);
        return world;
    }


    /**
     * Builds a world reusing an existing player, repositioned to the given coordinates.
     * Used when transitioning between rooms.
     *
     * @param player the player to reuse
     * @param x      spawn x-position in world pixels
     * @param y      spawn y-position in world pixels
     * @return a GameWorld with the given player
     */
    public GameWorld buildWithPlayer(Player player, float x, float y) {
        Objects.requireNonNull(player, "player must not be null");
        player.setPosition(x, y);

        GameWorld world = new GameWorld(room, player);
        world.addEntity(player);
        spawnEntities(world, player);
        return world;
    }

    private Player createPlayer() {
        for (SpawnPoint sp : room.getSpawnPoints()) {
            if (sp.getType() == SpawnPoint.Type.PLAYER) {
                return new Player(sp.getX(), sp.getY(), sound);
            }
        }
        throw new IllegalStateException("No player spawn point found in room: " + room);
    }

    private void spawnEntities(GameWorld world, Player player) {
        EntityFactory factory = new EntityFactory();
        factory.register(SpawnPoint.Type.FASTENEMY,     (x, y) -> new FastEnemy(x, y, player));
        factory.register(SpawnPoint.Type.NORMALENEMY,   (x, y) -> new NormalEnemy(x, y, player));
        factory.register(SpawnPoint.Type.SLOWENEMY,     (x, y) -> new SlowEnemy(x, y, player));
        factory.register(SpawnPoint.Type.HEALTHPOWERUP, (x, y) -> new HealthPowerup(x, y, sound));
        factory.register(SpawnPoint.Type.SPEEDPOWERUP,  (x, y) -> new SpeedPowerup(x, y, sound));
        factory.register(SpawnPoint.Type.ATTACKPOWERUP, (x, y) -> new AttackPowerup(x, y, sound));

        for (SpawnPoint sp : room.getSpawnPoints()) {
            if (sp.getType() != SpawnPoint.Type.PLAYER && sp.getType() != SpawnPoint.Type.DOOR) {
                world.addEntity(factory.spawn(sp));
            }
        }
    }
}