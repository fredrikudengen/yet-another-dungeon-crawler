package inf112.dungeoncrawler.model.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import inf112.dungeoncrawler.model.world.Room;
import org.junit.jupiter.api.Test;

public class RoomFactoryTest {

	@Test
	void createRoomReturnsRoomInstance() {
		RoomFactory factory = () -> new Room(8, 6);

		Room room = factory.createRoom();

		assertNotNull(room);
	}

	@Test
	void createRoomReturnsRoomWithExpectedDimensions() {
		RoomFactory factory = () -> new Room(12, 7);

		Room room = factory.createRoom();

		assertEquals(12, room.getWidth());
		assertEquals(7, room.getHeight());
	}
}
