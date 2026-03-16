package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.entities.powerups.Powerup;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.view.render.EntityRenderer;
import inf112.skeleton.view.render.WorldRenderer;

import java.util.List;

public class GameView {

    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;

    private WorldRenderer worldRenderer;
    private EntityRenderer entityRenderer;

    private static final int TILE_SIZE = 32;

    public void create(double worldWidth, double worldHeight) {
        this.viewport = new FillViewport((float) worldWidth, (float) worldHeight);
        this.batch    = new SpriteBatch();
        this.font     = new BitmapFont();
        font.setColor(Color.WHITE);

        this.worldRenderer  = new WorldRenderer(batch, viewport.getCamera(), TILE_SIZE);
        this.entityRenderer = new EntityRenderer(batch, viewport.getCamera());

        Gdx.graphics.setForegroundFPS(60);
    }

    /**
     * @param deltaTime seconds since the last frame – pass {@code Gdx.graphics.getDeltaTime()}
     */
    public void render(float deltaTime, Room room, List<Entity> entities, List<Powerup> powerups) {
        ScreenUtils.clear(Color.BLACK);

        batch.begin();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        try {
            worldRenderer.render(room);
            entityRenderer.render(deltaTime, entities, powerups);
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
    }

    public int getTileSize()       { return TILE_SIZE; }
    public Viewport getViewport()  { return viewport;  }
}