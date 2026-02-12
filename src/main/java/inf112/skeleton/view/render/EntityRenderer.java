package inf112.skeleton.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import inf112.skeleton.model.entities.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityRenderer {
    private final SpriteBatch batch;
    private final Camera camera;
    private final Map<String, Texture> entityTextures;

    public EntityRenderer(SpriteBatch batch, Camera camera) {
        this.batch = batch;
        this.camera = camera;
        this.entityTextures = new HashMap<>();
    }

    public void render(List<Entity> entities) {
        batch.setProjectionMatrix(camera.combined);

        for (Entity entity : entities) {
            String textureName = entity.getTextureName();
            Texture texture = getTexture(textureName);

            if (texture != null) {
                batch.draw(
                        texture,
                        entity.getX(),
                        entity.getY(),
                        entity.getWidth(),
                        entity.getHeight()
                );
            }
        }
    }

    private Texture getTexture(String textureName) {
        if (!entityTextures.containsKey(textureName)) {
            FileHandle file = Gdx.files.internal(textureName);

            Texture texture = new Texture(file);
            entityTextures.put(textureName, texture);
            }
        return entityTextures.get(textureName);
    }
}