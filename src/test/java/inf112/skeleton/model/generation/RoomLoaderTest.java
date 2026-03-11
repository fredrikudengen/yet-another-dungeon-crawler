package inf112.skeleton.model.generation;

import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.model.world.Room;
import inf112.skeleton.model.world.TileType;
import org.junit.jupiter.api.Test;

class RoomLoaderTest {

    @Test
    void testParseLinesUnixLineEndings() {
        String[] lines = RoomLoader.parseLines("###\n#.#\n###");
        assertEquals(3, lines.length);
    }

    @Test
    void testParseLinesWindowsLineEndings() {
        String[] lines = RoomLoader.parseLines("###\r\n#.#\r\n###");
        assertEquals(3, lines.length);
        assertEquals("###", lines[0]);
        assertEquals("#.#", lines[1]);
    }

    @Test
    void testParsedRoomDimensions() {
        String content = "####\n#..#\n####";
        String[] lines = RoomLoader.parseLines(content);
        Room room = new RoomTemplate(lines).createRoom();
        assertEquals(4, room.getWidth());
        assertEquals(3, room.getHeight());
    }

    @Test
    void testHashBecomesWall() {
        String content = "###\n#.#\n###";
        String[] lines = RoomLoader.parseLines(content);
        Room room = new RoomTemplate(lines).createRoom();
        assertEquals(TileType.WALL, room.getTileAt(0, 0));
    }

    @Test
    void testDotBecomesFloor() {
        String content = "###\n#.#\n###";
        String[] lines = RoomLoader.parseLines(content);
        Room room = new RoomTemplate(lines).createRoom();
        assertEquals(TileType.FLOOR, room.getTileAt(1, 1));
    }

    @Test
    void testSingleRowRoom() {
        String[] lines = RoomLoader.parseLines("###");
        Room room = new RoomTemplate(lines).createRoom();
        assertEquals(3, room.getWidth());
        assertEquals(1, room.getHeight());
        assertEquals(TileType.WALL, room.getTileAt(0, 0));
    }
}
