package inf112.skeleton.model.generation;

import com.badlogic.gdx.Gdx;
import inf112.skeleton.model.factories.RoomFactory;
import inf112.skeleton.model.world.Room;

public class RoomLoader implements RoomFactory {
    private final String filePath;

    public RoomLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Room createRoom() {
        String content = Gdx.files.internal(filePath).readString();
        String[] lines = parseLines(content);
        return new RoomTemplate(lines).createRoom();
    }

    static String[] parseLines(String content) {
        return content.replace("\r", "").split("\n");
    }
}
