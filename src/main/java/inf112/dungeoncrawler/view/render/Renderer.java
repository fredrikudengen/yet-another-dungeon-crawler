package inf112.dungeoncrawler.view.render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Renderer {
    protected final SpriteBatch batch;
    protected final Camera camera;

    protected Renderer(SpriteBatch batch, Camera camera) {
        this.batch  = batch;
        this.camera = camera;
    }

    public abstract void dispose();
}