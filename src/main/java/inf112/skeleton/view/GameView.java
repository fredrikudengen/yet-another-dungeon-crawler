package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.skeleton.model.world.Room;
import inf112.skeleton.view.render.WorldRenderer;
import inf112.skeleton.view.render.EntityRenderer;

public class GameView {
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;

    private WorldRenderer worldRenderer;

    private final int TILE_SIZE = 32; // Each tile is 32x32 pixels

    public void create(double worldWidth, double worldHeight) {
        this.viewport = new FillViewport((float) worldWidth, (float) worldHeight);
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);

        // Create the renderers
        this.worldRenderer = new WorldRenderer(batch, viewport.getCamera(), TILE_SIZE);

        Gdx.graphics.setForegroundFPS(60);
    }

    /**
     * Main render method called every frame.
     *
     * @param currentRoom The room currently being displayed
     */
    public void render(Room currentRoom) {
        // Clear the screen
        ScreenUtils.clear(Color.BLACK);

        // Begin drawing
        batch.begin();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        try {
            // 1. Draw the room
            worldRenderer.render(currentRoom);

            // 2. Draw entities

            // 3. Draw UI

        } finally {
            batch.end();
        }
    }

    public void updateCamera(float playerX, float playerY) {
        viewport.getCamera().position.set(playerX, playerY, 0);
        viewport.getCamera().update();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        worldRenderer.dispose();
        // entityRenderer.dispose();
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    public Viewport getViewport() {
        return viewport;
    }
}