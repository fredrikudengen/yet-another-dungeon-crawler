package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.entities.Powerup;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.view.render.WorldRenderer;
import inf112.skeleton.view.render.EntityRenderer;

import java.util.List;

/**
 * Example GameView showing how to integrate WorldRenderer.
 * This replaces/extends the AppView from the example.
 */
public class GameView {
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;

    // Renderers for different parts of the game
    private WorldRenderer worldRenderer;
    private EntityRenderer entityRenderer;

    private final int TILE_SIZE = 32; // Each tile is 32x32 pixels

    /**
     * Initialize the view with world dimensions.
     *
     * @param worldWidth Width of the viewport in pixels
     * @param worldHeight Height of the viewport in pixels
     */
    public void create(double worldWidth, double worldHeight) {
        // Setup viewport and camera
        this.viewport = new FillViewport((float) worldWidth, (float) worldHeight);
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);

        // Create the renderers
        this.worldRenderer = new WorldRenderer(batch, viewport.getCamera(), TILE_SIZE);
        this.entityRenderer = new EntityRenderer(batch, viewport.getCamera());

        Gdx.graphics.setForegroundFPS(60);
    }

    public void render(Room room, List<Entity> entities, List<Powerup> powerups) {
        ScreenUtils.clear(Color.BLACK);

        batch.begin();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        try {
            worldRenderer.render(room);

            entityRenderer.render(entities, powerups);

        } finally {
            batch.end();
        }
    }

    /**
     * Updates the camera to follow the player.
     * Call this before rendering.
     *
     * @param playerX Player's X position in world coordinates
     * @param playerY Player's Y position in world coordinates
     */
    public void updateCamera(float playerX, float playerY) {
        viewport.getCamera().position.set(playerX, playerY, 0);
        viewport.getCamera().update();
    }

    /**
     * Called when window is resized.
     */
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * Cleanup resources.
     */
    public void dispose() {
        batch.dispose();
        font.dispose();
        worldRenderer.dispose();
        // entityRenderer.dispose();
    }

    /**
     * Get the tile size used by the renderer.
     * Useful for collision detection and positioning.
     */
    public int getTileSize() {
        return TILE_SIZE;
    }

    public Viewport getViewport() {
        return viewport;
    }
}