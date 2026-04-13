package inf112.dungeoncrawler.model.world;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.enemies.Enemy;
import inf112.dungeoncrawler.model.entities.powerups.Powerup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld implements Iterable<Entity> {

    private final Room room;
    private final List<Entity> entities;
    private final Player player;
    private int enemiesKilled = 0;

    public GameWorld(Room room, Player player) {
        this.room     = room;
        this.player   = player;
        this.entities = new ArrayList<>();
    }

    public void step(double deltaTime) {
        for (Entity entity : entities) {
            entity.step(this, deltaTime);
        }

        entities.removeIf(entity -> {
            if (!entity.isAlive()) {
                if (!(entity instanceof Powerup) && !(entity instanceof Player)) {
                    enemiesKilled++;
                }
                return true;
            }
            return false;
        });
    }

    public boolean hasEnemiesRemaining() {
        return entities.stream().anyMatch(e -> e instanceof Enemy);
    }

    public void openDoors() {
        room.openDoors();
    }

    @Override
    public Iterator<Entity> iterator() { return entities.iterator(); }

    public Room getRoom()              { return room;          }
    public List<Entity> getEntities()  { return entities;      }
    public Player getPlayer()          { return player;        }
    public int getKilledEnemies()      { return enemiesKilled; }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}