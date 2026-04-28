package inf112.dungeoncrawler.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.view.render.EntityRenderer;
import inf112.dungeoncrawler.view.render.UIRenderer;
import inf112.dungeoncrawler.view.render.WorldRenderer;

/**
 * Composes the three renderer components WorldRenderer, EntityRenderer,
 * UIRenderer into a single render pass for one game frame.
 */
public class GameView {

    private Viewport viewport;
    private SpriteBatch batch;

    private WorldRenderer worldRenderer;
    private EntityRenderer entityRenderer;
    private UIRenderer uiRenderer;

    /**
     * Initializes the viewport, sprite batch, and all sub-renderers.
     *
     * @param worldWidth  viewport width in world pixels
     * @param worldHeight viewport height in world pixels
     */
    public void create(int worldWidth, int worldHeight) {
        this.viewport = new FillViewport(worldWidth, worldHeight);
        this.batch    = new SpriteBatch();

        this.worldRenderer  = new WorldRenderer(batch, viewport.getCamera());
        this.entityRenderer = new EntityRenderer(batch, viewport.getCamera());
        this.uiRenderer     = new UIRenderer(batch, viewport.getCamera());
    }

    /**
     * Renders the world, all entities, and the HUD for the current frame.
     *
     * @param deltaTime elapsed time since last frame in seconds
     * @param world     the game world to render
     */
    public void render(float deltaTime, GameWorld world) {
        ScreenUtils.clear(Color.BLACK);

        batch.begin();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        try {
            worldRenderer.render(world.getRoom());
            entityRenderer.render(deltaTime, world.getEntities());
            uiRenderer.renderPlayerHealth(world.getPlayer().getHealth());
            uiRenderer.renderEnemiesRemaining(world.getEnemiesRemainingCount());
        } finally {
            batch.end();
        }
    }

    /**
     * Centres the camera on the given world-pixel position.
     *
     * @param playerX x-coordinate to center on
     * @param playerY y-coordinate to center on
     */
    public void updateCamera(float playerX, float playerY) {
        viewport.getCamera().position.set(playerX, playerY, 0);
        viewport.getCamera().update();
    }

    /**
     * Updates the viewport when the window is resized.
     *
     * @param width  new window width in screen pixels
     * @param height new window height in screen pixels
     */
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /** Releases all resources held by the batch and sub-renderers. */
    public void dispose() {
        batch.dispose();
        worldRenderer.dispose();
        entityRenderer.dispose();
        uiRenderer.dispose();
    }
}