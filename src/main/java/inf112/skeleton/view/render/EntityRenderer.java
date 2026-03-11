package inf112.skeleton.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.entities.powerups.Powerup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityRenderer {
    private final SpriteBatch batch;
    private final Camera camera;

    // Consider combining entityTextures and powerupTextures
    private final Map<String, Texture> entityTextures;
    private final Map<String, Texture> powerupTextures;

    public EntityRenderer(SpriteBatch batch, Camera camera) {
        this.batch = batch;
        this.camera = camera;
        this.entityTextures = new HashMap<>();
        this.powerupTextures = new HashMap<>();
    }

    public void render(List<Entity> entities, List<Powerup> powerups) {
        batch.setProjectionMatrix(camera.combined);

        for (Entity entity : entities) {
            String textureName = entity.getTextureName();
            Texture texture = getEntityTexture(textureName);

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
        for (Powerup powerup : powerups) {
            String textureName = powerup.getTextureName();
            Texture texture = getPowerupTexture(textureName);

            if (texture != null) {
                batch.draw(
                        texture,
                        powerup.getX(),
                        powerup.getY(),
                        powerup.getWidth(),
                        powerup.getHeight()
                );
            }
        }
    }

    private Texture getEntityTexture(String textureName) {
        if (!entityTextures.containsKey(textureName)) {
            FileHandle file = Gdx.files.internal(textureName);

            Texture texture = new Texture(file);
            entityTextures.put(textureName, texture);
            }
        return entityTextures.get(textureName);
    }
    private Texture getPowerupTexture(String textureName) {
        if (!powerupTextures.containsKey(textureName)) {
            FileHandle file = Gdx.files.internal(textureName);

            Texture texture = new Texture(file);
            powerupTextures.put(textureName, texture);
        }
        return powerupTextures.get(textureName);
    }
}