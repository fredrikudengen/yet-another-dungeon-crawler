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

public class GameView {

    private Viewport viewport;
    private SpriteBatch batch;

    private WorldRenderer worldRenderer;
    private EntityRenderer entityRenderer;
    private UIRenderer uiRenderer;

    public void create(int worldWidth, int worldHeight) {
        this.viewport = new FillViewport(worldWidth, worldHeight);
        this.batch    = new SpriteBatch();

        this.worldRenderer  = new WorldRenderer(batch, viewport.getCamera());
        this.entityRenderer = new EntityRenderer(batch, viewport.getCamera());
        this.uiRenderer     = new UIRenderer(batch, viewport.getCamera());
    }

    public void render(float deltaTime, GameWorld world) {
        ScreenUtils.clear(Color.BLACK);

        batch.begin();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        try {
            worldRenderer.render(world.getRoom());
            entityRenderer.render(deltaTime, world.getEntities());
            uiRenderer.renderPlayerHealth(world.getPlayer().getHealth());
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
        worldRenderer.dispose();
        entityRenderer.dispose();
        uiRenderer.dispose();
    }
}