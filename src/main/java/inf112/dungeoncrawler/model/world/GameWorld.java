package inf112.dungeoncrawler.model.world;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.powerups.Powerup;
import inf112.dungeoncrawler.sound.SoundPlayer;
import inf112.dungeoncrawler.sound.SoundService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld implements Iterable<Entity> {
    private Room room;
    private List<Entity> entities;
    private int enemiesKilled;
    private Player player;

    public GameWorld(Room room, Player player) {
        this.room = room;
        this.player = player;
        this.entities = new ArrayList<>();
        this.enemiesKilled = 0;

    }

    public void step(double deltaTime) {
        for (Entity entity : entities) {
            entity.step(this, deltaTime);
        }

        // TODO: fix enemiesKilled (powerups are in entities as well)
        int before = entities.size();
        entities.removeIf(entity -> !entity.isAlive());
        enemiesKilled += before - entities.size();
    }

    @Override
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }

    public Room getRoom() {
        return room;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Player getPlayer() {
        return player;
    }

    public int getKilledEnemies() {
        return enemiesKilled;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}