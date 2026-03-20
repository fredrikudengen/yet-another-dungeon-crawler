package inf112.dungeoncrawler.model.world;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.powerups.Powerup;
import inf112.dungeoncrawler.model.sound.SoundPlayer;
import inf112.dungeoncrawler.model.sound.SoundService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld implements Iterable<Entity> {
    private Room room;
    private List<Entity> entities;
    private List<Powerup> powerups;
    private SoundService sound;
    private int enemiesKilled;

    public GameWorld(Room room, SoundService sound) {
        this.room = room;
        this.sound = sound;
        this.entities = new ArrayList<>();
        this.powerups = new ArrayList<>();
        // -1 because player is in entities
        this.enemiesKilled = -1;

    }

    public void step(double deltaTime, Player player) {

        for (Entity entity : entities) {
            entity.step(this, deltaTime);
        }

        for (Powerup powerup : powerups) {
            powerup.step(player, deltaTime);
        }

        int before = entities.size();
        entities.removeIf(entity -> entity.getHealth() <= 0);
        enemiesKilled += before - entities.size();

        powerups.removeIf(powerup -> {
            if (powerup.collidesWith(player)) {
                sound.play(SoundPlayer.SoundEffect.POWERUP_PICKUP);
                return true;
            }
            return false;
        });
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addPowerup(Powerup powerup) {
        powerups.add(powerup);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }

    public List<Powerup> getPowerups() {
        return powerups;
    }

    public int getKilledEnemies() {
        return enemiesKilled;
    }
}