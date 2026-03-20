package inf112.dungeoncrawler.model.generation;

import inf112.dungeoncrawler.model.factories.RoomFactory;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.model.world.TileType;
import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

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
             String row = pattern[height - 1 - y]; // read from the bottom to match LibGDX's y = 0 at the bottom
             for (int x = 0; x < Math.min(width, row.length()); x++) {
                char c = row.charAt(x);
                room.setTileAt(x, y, charToTileType(c));

                if (c == 'P') room.addSpawnPoint(new SpawnPoint(x * TILE_SIZE, y * TILE_SIZE, SpawnPoint.Type.PLAYER));
                else if (c == 'F') room.addSpawnPoint(new SpawnPoint(x * TILE_SIZE, y * TILE_SIZE, SpawnPoint.Type.FASTENEMY));
                else if (c == 'N') room.addSpawnPoint(new SpawnPoint(x * TILE_SIZE, y * TILE_SIZE, SpawnPoint.Type.NORMALENEMY));
                else if (c == 'L') room.addSpawnPoint(new SpawnPoint(x * TILE_SIZE, y * TILE_SIZE, SpawnPoint.Type.SLOWENEMY));
                else if (c == 'S') room.addSpawnPoint(new SpawnPoint(x * TILE_SIZE, y * TILE_SIZE, SpawnPoint.Type.SPEEDPOWERUP));
                else if (c == 'H') room.addSpawnPoint(new SpawnPoint(x * TILE_SIZE, y * TILE_SIZE, SpawnPoint.Type.HEALTHPOWERUP));
                else if (c == 'A') room.addSpawnPoint(new SpawnPoint(x * TILE_SIZE, y * TILE_SIZE, SpawnPoint.Type.ATTACKPOWERUP));
             }
        }
        return room;
    }

    private TileType charToTileType(char c) {
        return switch (c) {
            case '#' -> TileType.WALL;
            case ' ' -> TileType.EMPTY;
            default -> TileType.FLOOR;
        };
    }
}