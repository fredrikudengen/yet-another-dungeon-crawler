package inf112.dungeoncrawler.model.generation;

import com.badlogic.gdx.Gdx;
import inf112.dungeoncrawler.model.factories.RoomFactory;
import inf112.dungeoncrawler.model.world.Room;

public class RoomLoader implements RoomFactory {

    private final String filePath;

    /**
     * Loads a room from an internal asset file and delegates parsing to RoomTemplate.
     * The file must use the same character format as RoomTemplate.
     *
     * @param filePath path to the room file relative to the assets directory,
     */
    public RoomLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Room createRoom() {
        String content = Gdx.files.internal(filePath).readString();
        String[] lines = parseLines(content);
        return new RoomTemplate(lines).createRoom();
    }

    private static String[] parseLines(String content) {
        return content.replace("\r", "").split("\n");
    }
}
