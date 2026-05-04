package inf112.dungeoncrawler.view.render;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Movable;
import inf112.dungeoncrawler.model.entities.powerups.Powerup;

import java.util.WeakHashMap;

/**
 * Determines the correct texture filename for an entity each frame,
 * based on movement state, facing direction, and attack animation.
 * Walk-cycle state is tracked per entity using a WeakHashMap
 * so entries are garbage-collected when entities are removed.
 */
public class AnimationController {

    private static class WalkState {
        float timer = 0f;
        int   frame = 1;
    }

    private final WeakHashMap<Entity, WalkState> states = new WeakHashMap<>();

    /**
     * Returns texture path to be used in EntityRenderer.
     *
     * @param deltaTime time since last call
     * @param entity specific entity to get texture for
     * @param basePath path to entity texture in EntityRenderer.TEXTURE_PATHS
     * @return texture path of given entity
     */
    public String getTextureName(float deltaTime, Entity entity, String basePath) {
        if (entity instanceof Powerup p && p.isPickedUp()) return null;

        if (!(entity instanceof Movable movable)) {
            return basePath;
        }

        if (movable.isAttacking()) {
            String dir = directionToString(movable.getFacingDirection());
            return "player/weapon/attack/Player_attack_weapon_" + dir + movable.getAttackFrame() + ".png";
        }

        return getWalkTexture(deltaTime, entity, movable, basePath);
    }

    private String getWalkTexture(float deltaTime, Entity entity, Movable movable, String basePath) {
        WalkState state = states.computeIfAbsent(entity, e -> new WalkState());

        if (movable.isMoving()) {
            state.timer += deltaTime;
            if (state.timer >= movable.getFrameDuration()) {
                state.timer -= movable.getFrameDuration();
                state.frame  = (state.frame == 1) ? 2 : 1;
            }
        } else {
            state.timer = 0f;
            state.frame = 1;
        }

        String dir = directionToString(movable.getFacingDirection());
        return basePath + dir + state.frame + ".png";
    }

    private static String directionToString(Direction direction) {
        return switch (direction) {
            case UP    -> "up";
            case LEFT  -> "left";
            case RIGHT -> "right";
            case DOWN  -> "down";
        };
    }
}