package inf112.skeleton.model.factories;

import inf112.skeleton.model.world.Room;

/**
 * Factory interface for creating rooms.
 * Different implementations can create different types of rooms.
 */
public interface RoomFactory {
    /**
     * Creates and returns a new Room instance.
     *
     * @return A newly created room
     */
    Room createRoom();
}