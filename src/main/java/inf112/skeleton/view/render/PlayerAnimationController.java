package inf112.skeleton.view.render;

import inf112.skeleton.model.Direction;
import inf112.skeleton.model.entities.Player;

public class PlayerAnimationController {

    private static final float WALK_FRAME_DURATION = 0.15f;

    private static final float ATTACK_DURATION = Player.ATTACK_DURATION;

    private static final String BASE_PATH = "src/main/resources/Player/Weapon1";

    private float walkTimer = 0f;
    private int   walkFrame = 1; // alternates between 1 and 2

    public void update(float deltaTime, Player player) {
        if (player.isMoving() && !player.isAttacking()) {
            walkTimer += deltaTime;
            if (walkTimer >= WALK_FRAME_DURATION) {
                walkTimer -= WALK_FRAME_DURATION;
                walkFrame  = (walkFrame == 1) ? 2 : 1;
            }
        } else {
            walkTimer = 0f;
            walkFrame = 1;
        }
    }

    public String getTextureName(Player player) {
        String dir = directionToString(player.getFacingDirection());

        if (player.isAttacking()) {
            // First half of the attack duration → charge; second half → swing
            int attackFrame = (player.getAttackTimer() < ATTACK_DURATION / 2f) ? 1 : 2;
            return BASE_PATH + "/Attack/Player_attack_weapon_" + dir + attackFrame + ".png";
        }

        return BASE_PATH + "/Walk/Player_walk_weapon_" + dir + walkFrame + ".png";
    }

    private static String directionToString(Direction direction) {
        switch (direction) {
            case UP:    return "up";
            case DOWN:  return "down";
            case LEFT:  return "left";
            default:    return "right";
        }
    }
}