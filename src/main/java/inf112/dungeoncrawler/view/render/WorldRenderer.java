package inf112.dungeoncrawler.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Camera;

import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.model.world.TileType;
import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

import java.util.HashMap;
import java.util.Map;

public class WorldRenderer {
    private final SpriteBatch batch;
    private final Camera camera;
    private final Map<TileType, Texture> tileTextures;

    public WorldRenderer(SpriteBatch batch, Camera camera) {
        this.batch = batch;
        this.camera = camera;
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
                    float worldX = x * TILE_SIZE;
                    float worldY = y * TILE_SIZE;

                    batch.draw(texture, worldX, worldY, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    public void dispose() {
        for (Texture texture : tileTextures.values()) {
            if (texture != null) {
                texture.dispose();
            }
        }
        tileTextures.clear();
    }
}