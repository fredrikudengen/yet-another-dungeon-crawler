package inf112.dungeoncrawler.model.generation;

import inf112.dungeoncrawler.model.factories.RoomFactory;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.model.world.TileType;

import java.util.Map;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public class RoomTemplate implements RoomFactory {

    private static final Map<Character, SpawnPoint.Type> SPAWN_CHARS = Map.of(
            'P', SpawnPoint.Type.PLAYER,
            'F', SpawnPoint.Type.FASTENEMY,
            'N', SpawnPoint.Type.NORMALENEMY,
            'L', SpawnPoint.Type.SLOWENEMY,
            'S', SpawnPoint.Type.SPEEDPOWERUP,
            'H', SpawnPoint.Type.HEALTHPOWERUP,
            'A', SpawnPoint.Type.ATTACKPOWERUP,
            'D', SpawnPoint.Type.DOOR
    );

    private final String[] pattern;
    private final int width;
    private final int height;

    public RoomTemplate(String[] pattern) {
        this.pattern = pattern;
        this.height  = pattern.length;
        this.width   = pattern[0].length();
    }

    @Override
    public Room createRoom() {
        Room room = new Room(width, height);

        for (int y = 0; y < height; y++) {
            // Read from the bottom to match libGDX's y = 0 at the bottom
            String row = pattern[height - 1 - y];
            for (int x = 0; x < Math.min(width, row.length()); x++) {
                char c = row.charAt(x);
                room.setTileAt(x, y, charToTileType(c, x, y));

                SpawnPoint.Type spawnType = SPAWN_CHARS.get(c);
                if (spawnType != null) {
                    room.addSpawnPoint(new SpawnPoint(x * TILE_SIZE, y * TILE_SIZE, spawnType));
                }
            }
        }
        return room;
    }

    private TileType charToTileType(char c, int x, int y) {
        return switch (c) {
            case '#' -> TileType.WALL;
            case ' ' -> TileType.EMPTY;
            case 'D' -> doorTileForWall(x, y);
            default  -> TileType.FLOOR;
        };
    }

    private TileType doorTileForWall(int x, int y) {
        boolean onLeftWall = x == 0 && y > 0 && y < height - 1;
        boolean onRightWall = x == width - 1 && y > 0 && y < height - 1;
        boolean onTopWall = y == height - 1 && x > 0 && x < width - 1;
        boolean onBottomWall = y == 0 && x > 0 && x < width - 1;

        if (onLeftWall) {
            return TileType.DOOR_LEFT_WALL_CLOSED;
        }
        if (onRightWall) {
            return TileType.DOOR_RIGHT_WALL_CLOSED;
        }
        if (onTopWall) {
            return TileType.DOOR_TOP_CLOSED;
        }
        if (onBottomWall) {
            return TileType.DOOR_BOTTOM_CLOSED;
        }
        return TileType.DOOR_CLOSED;
    }
}