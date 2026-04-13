package inf112.dungeoncrawler.view.render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class UIRenderer extends Renderer {

	private final Texture heartTexture;

	private static final float HUD_MARGIN = 50f;
	private static final float HEART_SIZE = 42f;
	private static final float HEART_SPACING = 16f;

	public UIRenderer(SpriteBatch batch, Camera camera) {
        super(batch, camera);
		this.heartTexture = new Texture("powerups/Life.png");
	}

	public void renderPlayerHealth(int health) {
		Matrix4 previousProjection = batch.getProjectionMatrix().cpy();
		batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0,
				camera.viewportWidth, camera.viewportHeight));

		int visibleHearts = Math.max(0, health);
		float y = camera.viewportHeight - HUD_MARGIN - HEART_SIZE;
		for (int i = 0; i < visibleHearts; i++) {
			float x = HUD_MARGIN + i * (HEART_SIZE + HEART_SPACING);
			batch.draw(heartTexture, x, y, HEART_SIZE, HEART_SIZE);
		}

		batch.setProjectionMatrix(previousProjection);
	}

	@Override
	public void dispose() {
		heartTexture.dispose();
	}
}
