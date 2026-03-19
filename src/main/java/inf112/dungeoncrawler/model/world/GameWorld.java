package inf112.dungeoncrawler.model.world;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.powerups.Powerup;
import inf112.dungeoncrawler.model.sound.SoundPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld implements Iterable<Entity> {
    private Room room;
    private List<Entity> entities;
    private List<Powerup> powerups;

    public GameWorld(Room room) {
        this.room = room;
        this.entities = new ArrayList<>();
        this.powerups = new ArrayList<>();
    }

    public void step(double deltaTime, Player player) {

        for (Entity entity : entities) {
            entity.step(this, deltaTime);
        }

        for (Powerup powerup : powerups) {
            powerup.step(player, deltaTime);
        }

        entities.removeIf(entity -> entity.getHealth() <= 0);

        powerups.removeIf(powerup -> {
            if (powerup.collidesWith(player)) {
                SoundPlayer.getInstance().play(SoundPlayer.SoundEffect.POWERUP_PICKUP);
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
}