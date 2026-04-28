package inf112.dungeoncrawler.view.render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Renderer {
    protected final SpriteBatch batch;
    protected final Camera camera;

    /**
     * Abstract base class for all renderer components used by GameView.
     *
     * @param batch  the sprite batch to draw into
     * @param camera the camera whose projection matrix is applied before drawing
     */
    protected Renderer(SpriteBatch batch, Camera camera) {
        this.batch  = batch;
        this.camera = camera;
    }

    /** Releases any resources held by this renderer. */
    public abstract void dispose();
}