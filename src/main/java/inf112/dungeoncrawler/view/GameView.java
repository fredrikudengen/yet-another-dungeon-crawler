package inf112.dungeoncrawler.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.powerups.Powerup;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import inf112.dungeoncrawler.view.render.EntityRenderer;
import inf112.dungeoncrawler.view.render.UIRenderer;
import inf112.dungeoncrawler.view.render.WorldRenderer;

import java.util.List;

public class GameView {

    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;

    private WorldRenderer worldRenderer;
    private EntityRenderer entityRenderer;
    private UIRenderer uiRenderer;

    public void create(double worldWidth, double worldHeight) {
        this.viewport = new FillViewport((float) worldWidth, (float) worldHeight);
        this.batch    = new SpriteBatch();
        this.font     = new BitmapFont();
        font.setColor(Color.WHITE);

        this.worldRenderer  = new WorldRenderer(batch, viewport.getCamera());
        this.entityRenderer = new EntityRenderer(batch, viewport.getCamera());
        this.uiRenderer     = new UIRenderer(batch, viewport.getCamera());

        Gdx.graphics.setForegroundFPS(60);
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
        font.dispose();
        worldRenderer.dispose();
        entityRenderer.dispose();
        uiRenderer.dispose();
    }
}