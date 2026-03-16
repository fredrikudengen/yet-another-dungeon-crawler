package inf112.skeleton.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.entities.Player;
import inf112.skeleton.model.entities.enemies.Enemy;
import inf112.skeleton.model.entities.powerups.Powerup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityRenderer {

    private final SpriteBatch batch;
    private final Camera camera;

    private final Map<String, Texture> textureCache = new HashMap<>();

    private final PlayerAnimationController playerAnimator = new PlayerAnimationController();

    private final EnemyAnimationController enemyAnimator = new EnemyAnimationController();

    public EntityRenderer(SpriteBatch batch, Camera camera) {
        this.batch  = batch;
        this.camera = camera;
    }

    public void render(float deltaTime, List<Entity> entities, List<Powerup> powerups) {
        batch.setProjectionMatrix(camera.combined);

        for (Entity entity : entities) {
            String textureName = resolveTextureName(deltaTime, entity);
            Texture texture = getOrLoadTexture(textureName);
            if (texture != null) {
                batch.draw(texture, entity.getX(), entity.getY(),
                        entity.getWidth(), entity.getHeight());
            }
        }

        for (Powerup powerup : powerups) {
            Texture texture = getOrLoadTexture(powerup.getTextureName());
            if (texture != null) {
                batch.draw(texture, powerup.getX(), powerup.getY(),
                        powerup.getWidth(), powerup.getHeight());
            }
        }
    }

    private String resolveTextureName(float deltaTime, Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            playerAnimator.update(deltaTime, player);
            return playerAnimator.getTextureName(player);
        }
        else {
            return enemyAnimator.getTextureName(deltaTime, (Enemy) entity);
        }
    }

    private Texture getOrLoadTexture(String textureName) {
        if (textureName == null || textureName.isEmpty()) return null;

        if (!textureCache.containsKey(textureName)) {
            FileHandle file = Gdx.files.internal(textureName);
            textureCache.put(textureName, new Texture(file));
        }
        return textureCache.get(textureName);
    }

    public void dispose() {
        for (Texture texture : textureCache.values()) {
            if (texture != null) texture.dispose();
        }
        textureCache.clear();
    }
}