package inf112.dungeoncrawler.view.render;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Movable;
import inf112.dungeoncrawler.model.entities.powerups.Powerup;

import java.util.WeakHashMap;

public class AnimationController {

    private static final float PLAYER_FRAME_DURATION = 0.15f;
    private static final float ENEMY_FRAME_DURATION  = 0.18f;

    private static class WalkState {
        float timer = 0f;
        int   frame = 1;
    }

    private final WeakHashMap<Entity, WalkState> states = new WeakHashMap<>();

    public String getTextureName(float deltaTime, Entity entity) {
        if (entity instanceof Powerup powerup) {
            return powerup.isPickedUp() ? null : entity.getBaseTexturePath();
        }

        if (!(entity instanceof Movable movable)) {
            return entity.getBaseTexturePath();
        }

        if (movable.isAttacking()) {
            String dir = directionToString(movable.getFacingDirection());
            return "player/weapon/attack/Player_attack_weapon_" + dir + movable.getAttackFrame() + ".png";
        }

        return getWalkTexture(deltaTime, entity, movable);
    }

    private String getWalkTexture(float deltaTime, Entity entity, Movable movable) {
        WalkState state = states.computeIfAbsent(entity, e -> new WalkState());
        float frameDuration = (entity.getClass().getSimpleName().equals("Player"))
                ? PLAYER_FRAME_DURATION
                : ENEMY_FRAME_DURATION;

        if (movable.isMoving()) {
            state.timer += deltaTime;
            if (state.timer >= frameDuration) {
                state.timer -= frameDuration;
                state.frame  = (state.frame == 1) ? 2 : 1;
            }
        } else {
            state.timer = 0f;
            state.frame = 1;
        }

        String dir = directionToString(movable.getFacingDirection());

        return entity.getBaseTexturePath() + dir + state.frame + ".png";
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