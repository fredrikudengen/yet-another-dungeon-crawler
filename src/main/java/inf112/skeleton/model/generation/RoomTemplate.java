package inf112.skeleton.model.generation;

import inf112.skeleton.model.factories.RoomFactory;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.model.world.TileType;

/**
 * Represents a template for creating rooms with predefined layouts.
 * Templates can be defined as strings for easy visualization and editing.
 */
public class RoomTemplate implements RoomFactory {
    private final String[] pattern;
    private final int width;
    private final int height;

    /**
     * Creates a room template from a string array.
     * Each string represents a row of tiles.
     *
     * Supported characters:
     * '#' = Wall
     * '.' = Floor
     * 'D' = Door
     * 'P' = Pit
     * 'S' = Spike trap
     *
     * Example:
     * "########"
     * "#......#"
     * "#..##..#"
     * "#..##..#"
     * "#......#"
     * "####D###"
     *
     * @param pattern Array of strings defining the tile layout
     */
    public RoomTemplate(String[] pattern) {
        this.pattern = pattern;
        this.height = pattern.length;
        this.width = pattern.length > 0 ? pattern[0].length() : 0;
    }

    /**
     * Creates a Room instance based on this template.
     *
     * @return A new Room with tiles set according to the template
     */
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

    /**
     * Converts a character to a TileType.
     *
     * @param c The character to convert
     * @return The corresponding TileType
     */
    private TileType charToTileType(char c) {
        return switch (c) {
            case '#' -> TileType.WALL;
            default -> TileType.FLOOR;
        };
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}