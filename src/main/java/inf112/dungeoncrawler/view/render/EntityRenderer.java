package inf112.dungeoncrawler.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.dungeoncrawler.model.entities.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Renders all Entity instances each frame.
 * Textures are loaded on demand and cached for reuse.
 * Delegates animation frame selection to AnimationController.
 */
public class EntityRenderer extends Renderer {

    private final Map<String, Texture> textureCache = new HashMap<>();
    private final AnimationController animator = new AnimationController();

    public EntityRenderer(SpriteBatch batch, Camera camera) {
        super(batch, camera);
    }

    public void render(float deltaTime, List<Entity> entities) {
        batch.setProjectionMatrix(camera.combined);
        for (Entity entity : entities) {
            Texture texture = getOrLoadTexture(animator.getTextureName(deltaTime, entity));
            if (texture != null) {
                batch.draw(texture, entity.getX(), entity.getY(),
                        entity.getWidth(), entity.getHeight());
            }
        }
    }

    private Texture getOrLoadTexture(String textureName) {
        if (textureName == null || textureName.isEmpty()) return null;
        return textureCache.computeIfAbsent(textureName,
                name -> new Texture(Gdx.files.internal(name)));
    }

    @Override
    public void dispose() {
        textureCache.values().forEach(Texture::dispose);
        textureCache.clear();
    }
}