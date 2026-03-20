package inf112.dungeoncrawler.view.render;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.entities.enemies.Enemy;

import java.util.HashMap;

public class EnemyAnimationController {

    private static final float WALK_FRAME_DURATION = 0.18f;

    private static class WalkState {
        float timer = 0f;
        int   frame = 1; // alternates between 1 and 2
    }

    private final HashMap<Enemy, WalkState> states = new HashMap<>();

    public String getTextureName(float deltaTime, Enemy enemy) {
        WalkState state = states.computeIfAbsent(enemy, e -> new WalkState());

        if (enemy.isMoving()) {
            state.timer += deltaTime;
            if (state.timer >= WALK_FRAME_DURATION) {
                state.timer -= WALK_FRAME_DURATION;
                state.frame  = (state.frame == 1) ? 2 : 1;
            }
        } else {
            state.timer = 0f;
            state.frame = 1;
        }

        String dir = directionToString(enemy.getFacingDirection());
        return enemy.getBaseTexturePath() + "/enemy_" + dir + state.frame + ".png";
    }

    private static String directionToString(Direction direction) {
        return switch (direction) {
            case UP -> "up";
            case LEFT -> "left";
            case RIGHT -> "right";
            default -> "down";
        };
    }
}