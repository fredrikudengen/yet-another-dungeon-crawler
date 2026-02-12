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

/**
 * Renders grid-based rooms to the screen.
 * Handles texture loading, caching, and drawing of tiles.
 */
public class WorldRenderer {
    private final SpriteBatch batch;
    private final Camera camera;
    private final Map<TileType, Texture> tileTextures;
    private final int tileSize; // Size of each tile in pixels

    /**
     * Creates a new WorldRenderer.
     *
     * @param batch The SpriteBatch to use for rendering
     * @param camera The camera to use for projection
     * @param tileSize The size of each tile in pixels (e.g., 32 or 64)
     */
    public WorldRenderer(SpriteBatch batch, Camera camera, int tileSize) {
        this.batch = batch;
        this.camera = camera;
        this.tileSize = tileSize;
        this.tileTextures = new HashMap<>();

        loadTextures();
    }

    /**
     * Loads and caches all tile textures.
     * This is done once at startup to avoid reloading textures every frame.
     */
    private void loadTextures() {
        // Load textures for each tile type
        // The texture file names come from TileType.getTextureName()
        for (TileType type : TileType.values()) {
            String textureName = type.getTextureName();
            FileHandle file = Gdx.files.internal(textureName);

            if (file.exists()) {
                Texture texture = new Texture(file);
                tileTextures.put(type, texture);
            } else {
                // Log warning if texture is missing
                System.err.println("Warning: Texture not found: " + textureName);
                // You could create a default "missing texture" texture here
            }
        }
    }

    /**
     * Renders a room to the screen.
     * This should be called inside your main render loop,
     * between batch.begin() and batch.end().
     *
     * @param room The room to render
     */
    public void render(Room room) {
        // Set the projection matrix to use the camera's view
        batch.setProjectionMatrix(camera.combined);

        TileType[][] tiles = room.getTiles();
        int width = room.getWidth();
        int height = room.getHeight();

        // Iterate through all tiles and draw them
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TileType tileType = tiles[x][y];

                // Get the texture for this tile type
                Texture texture = tileTextures.get(tileType);

                if (texture != null) {
                    // Calculate world position (in pixels)
                    float worldX = x * tileSize;
                    float worldY = y * tileSize;

                    // Draw the tile
                    batch.draw(texture, worldX, worldY, tileSize, tileSize);
                }
            }
        }
    }

    /**
     * Draws a grid overlay on top of the tiles.
     * Useful for debugging and level editing.
     * Note: This requires ShapeRenderer, not shown here.
     *
     * @param room The room to draw grid for
     */
    public void renderDebugGrid(Room room) {
        // TODO: Use ShapeRenderer to draw grid lines
        // This would be implemented similarly to how the example
        // AppView draws shapes with ShapeRenderer
    }

    /**
     * Converts world coordinates to grid coordinates.
     * Useful for mouse picking or collision detection.
     *
     * @param worldX X position in world coordinates (pixels)
     * @param worldY Y position in world coordinates (pixels)
     * @return Array of [gridX, gridY]
     */
    public int[] worldToGrid(float worldX, float worldY) {
        int gridX = (int)(worldX / tileSize);
        int gridY = (int)(worldY / tileSize);
        return new int[] { gridX, gridY };
    }

    /**
     * Converts grid coordinates to world coordinates.
     * Returns the center of the tile.
     *
     * @param gridX X position in grid coordinates
     * @param gridY Y position in grid coordinates
     * @return Array of [worldX, worldY]
     */
    public float[] gridToWorld(int gridX, int gridY) {
        float worldX = gridX * tileSize + tileSize / 2f;
        float worldY = gridY * tileSize + tileSize / 2f;
        return new float[] { worldX, worldY };
    }

    /**
     * Disposes of all loaded textures.
     * Should be called when the renderer is no longer needed.
     */
    public void dispose() {
        for (Texture texture : tileTextures.values()) {
            if (texture != null) {
                texture.dispose();
            }
        }
        tileTextures.clear();
    }

    /**
     * Gets the size of tiles in pixels.
     *
     * @return Tile size in pixels
     */
    public int getTileSize() {
        return tileSize;
    }
}