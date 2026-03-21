package inf112.dungeoncrawler.view.render;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Movable;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.entities.powerups.Powerup;

import java.util.HashMap;

public class AnimationController {

    private static final float PLAYER_FRAME_DURATION = 0.15f;
    private static final float ENEMY_FRAME_DURATION  = 0.18f;

    private static class WalkState {
        float timer = 0f;
        int   frame = 1;
    }

    private final HashMap<Entity, WalkState> states = new HashMap<>();

    public String getTextureName(float deltaTime, Entity entity) {
        // Stops drawing, but keeps the powerup alive for its effects
        if (entity instanceof Powerup powerup && powerup.isPickedUp()) return null;

        if (entity instanceof Powerup) {
            return entity.getBaseTexturePath();
        }

        if (entity instanceof Player player && player.isAttacking()) {
            int attackFrame = (player.getAttackTimer() < Player.ATTACK_DURATION / 2f) ? 1 : 2;
            return "player/weapon/attack/Player_attack_weapon_"
                    + directionToString(player.getFacingDirection()) + attackFrame + ".png";
        }

        WalkState state = states.computeIfAbsent(entity, e -> new WalkState());
        float frameDuration = (entity instanceof Player) ? PLAYER_FRAME_DURATION : ENEMY_FRAME_DURATION;

        Movable movable = (Movable) entity;
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
            default    -> "down";
        };
    }
}