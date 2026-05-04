package inf112.dungeoncrawler.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.enemies.FastEnemy;
import inf112.dungeoncrawler.model.entities.enemies.NormalEnemy;
import inf112.dungeoncrawler.model.entities.enemies.SlowEnemy;
import inf112.dungeoncrawler.model.entities.powerups.AttackPowerup;
import inf112.dungeoncrawler.model.entities.powerups.HealthPowerup;
import inf112.dungeoncrawler.model.entities.powerups.SpeedPowerup;

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

    private static final Map<Class<?>, String> TEXTURE_PATHS = Map.of(
            Player.class,      "player/weapon/Player_walk_weapon_",
            FastEnemy.class,   "enemy/fast/enemy_",
            NormalEnemy.class, "enemy/normal/enemy_",
            SlowEnemy.class,   "enemy/slow/enemy_",
            HealthPowerup.class, "powerups/Life.png",
            SpeedPowerup.class, "powerups/faster.png",
            AttackPowerup.class, "powerups/weapon1.png"
    );


    public EntityRenderer(SpriteBatch batch, Camera camera) {
        super(batch, camera);
    }

    /**
     * Renders all entities. Called in GameView.
     *
     * @param deltaTime time since last frame
     * @param entities list of entities to be rendered
     */
    public void render(float deltaTime, List<Entity> entities) {
        batch.setProjectionMatrix(camera.combined);
        for (Entity entity : entities) {
            String basePath = getTexturePath(entity);
            String textureName = animator.getTextureName(deltaTime, entity, basePath);
            Texture texture = getOrLoadTexture(textureName);
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

    private String getTexturePath(Entity entity) {
        String path = TEXTURE_PATHS.get(entity.getClass());
        if (path == null) throw new IllegalArgumentException("No texture for: " + entity.getClass());
        return path;
    }

    @Override
    public void dispose() {
        textureCache.values().forEach(Texture::dispose);
        textureCache.clear();
    }
}