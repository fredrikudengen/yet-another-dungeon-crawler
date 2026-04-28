package inf112.dungeoncrawler.model.factories;

import inf112.dungeoncrawler.model.world.Room;

/**
 * Factory interface for creating Room instances.
 */
public interface RoomFactory {

    /**
     * Creates and returns a new Room.
     *
     * @return a fully initialized room with tiles and spawn points set
     */
    Room createRoom();
}