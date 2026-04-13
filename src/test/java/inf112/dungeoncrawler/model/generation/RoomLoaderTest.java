package inf112.dungeoncrawler.model.generation;

import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.model.world.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomLoaderTest {

    @Test
    void parseLines_unixLineEndings_splitCorrectly() {
        String[] lines = RoomLoader.parseLines("###\n#.#\n###");
        assertEquals(3, lines.length);
        assertEquals("###", lines[0]);
        assertEquals("#.#", lines[1]);
        assertEquals("###", lines[2]);
    }

    @Test
    void parseLines_windowsLineEndings_splitCorrectly() {
        String[] lines = RoomLoader.parseLines("###\r\n#.#\r\n###");
        assertEquals(3, lines.length);
        assertEquals("###", lines[0]);
        assertEquals("#.#", lines[1]);
        assertEquals("###", lines[2]);
    }

    @Test
    void parseLines_singleRow_returnsOneElement() {
        String[] lines = RoomLoader.parseLines("###");
        assertEquals(1, lines.length);
        assertEquals("###", lines[0]);
    }

    @Test
    void parsedRoom_hasExpectedDimensions() {
        Room room = roomFrom("####\n#..#\n####");
        assertEquals(4, room.getWidth());
        assertEquals(3, room.getHeight());
    }

    @Test
    void parsedRoom_hashBecomesWall() {
        Room room = roomFrom("###\n#.#\n###");
        assertEquals(TileType.WALL, room.getTileAt(0, 0));
    }

    @Test
    void parsedRoom_dotBecomesFloor() {
        Room room = roomFrom("###\n#.#\n###");
        assertEquals(TileType.FLOOR, room.getTileAt(1, 1));
    }

    @Test
    void parsedRoom_doorCharBecomesDoorClosed() {
        Room room = roomFrom("D");
        assertEquals(TileType.DOOR_CLOSED, room.getTileAt(0, 0));
    }

    private Room roomFrom(String content) {
        return new RoomTemplate(RoomLoader.parseLines(content)).createRoom();
    }
}