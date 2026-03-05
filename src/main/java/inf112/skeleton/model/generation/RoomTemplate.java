package inf112.skeleton.model.generation;

import inf112.skeleton.model.factories.RoomFactory;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.model.world.TileType;


public class RoomTemplate implements RoomFactory {
    private final String[] pattern;
    private final int width;
    private final int height;

    public RoomTemplate(String[] pattern) {
        this.pattern = pattern;
        this.height = pattern.length;
        this.width = pattern[0].length();
    }

    public Room createRoom() {
        Room room = new Room(width, height);

        for (int y = 0; y < height; y++) {
            String row = pattern[y];
            for (int x = 0; x < Math.min(width, row.length()); x++) {
                char c = row.charAt(x);
                TileType type = charToTileType(c);
                room.setTileAt(x, y, type);
            }
        }
        return room;
    }

    private TileType charToTileType(char c) {
        return switch (c) {
            case '#' -> TileType.WALL;
            default -> TileType.FLOOR;
        };
    }
}