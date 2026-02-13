package inf112.skeleton.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Camera;

import inf112.skeleton.model.world.Room;
import inf112.skeleton.model.world.TileType;

import java.util.HashMap;
import java.util.Map;

public class WorldRenderer {
    private final SpriteBatch batch;
    private final Camera camera;
    private final Map<TileType, Texture> tileTextures;
    private final int tileSize; // Size of each tile in pixels

    public WorldRenderer(SpriteBatch batch, Camera camera, int tileSize) {
        this.batch = batch;
        this.camera = camera;
        this.tileSize = tileSize;
        this.tileTextures = new HashMap<>();

        loadTextures();
    }

    private void loadTextures() {
        for (TileType type : TileType.values()) {
            String textureName = type.getTextureName();
            FileHandle file = Gdx.files.internal(textureName);

            Texture texture = new Texture(file);
            tileTextures.put(type, texture);

        }
    }

    public void render(Room room) {
        batch.setProjectionMatrix(camera.combined);

        TileType[][] tiles = room.getTiles();
        int width = room.getWidth();
        int height = room.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TileType tileType = tiles[x][y];

                Texture texture = tileTextures.get(tileType);

                if (texture != null) {
                    float worldX = x * tileSize;
                    float worldY = y * tileSize;

                    batch.draw(texture, worldX, worldY, tileSize, tileSize);
                }
            }
        }
    }

    public int[] worldToGrid(float worldX, float worldY) {
        int gridX = (int)(worldX / tileSize);
        int gridY = (int)(worldY / tileSize);
        return new int[] { gridX, gridY };
    }

    public float[] gridToWorld(int gridX, int gridY) {
        float worldX = gridX * tileSize + tileSize / 2f;
        float worldY = gridY * tileSize + tileSize / 2f;
        return new float[] { worldX, worldY };
    }

    public void dispose() {
        for (Texture texture : tileTextures.values()) {
            if (texture != null) {
                texture.dispose();
            }
        }
        tileTextures.clear();
    }

    public int getTileSize() {
        return tileSize;
    }
}