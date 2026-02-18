package inf112.skeleton.model.entities;

import inf112.skeleton.model.combat.AttackType;
import inf112.skeleton.model.combat.Attack;
import java.util.HashMap;
import java.util.Map;
import inf112.skeleton.model.world.GameWorld;

public class Player extends Entity {
    private boolean movingLeft, movingRight, movingUp, movingDown;
    private Map<AttackType, Attack> attacks;
    private AttackType currentAttackType = AttackType.BASIC;
    private float attackTimer = 0;
    private boolean isAttacking = false;


    public Player(float x, float y) {
        super(x, y, 32, 32, 200);

        attacks = new HashMap<>();
        attacks.put(AttackType.BASIC, Attack.basicAttack());
        attacks.put(AttackType.HEAVY, Attack.heavyAttack());
    }

    @Override
    public void step(GameWorld world, double deltaTime) {

        for (Attack attack : attacks.values()) {
            attack.update((float) deltaTime);
        }

        if (isAttacking) {
            attackTimer += (float) deltaTime;
            if (attackTimer > 0.2f) {
                isAttacking = false;
                attackTimer = 0;
            }
        }

        if (!isAttacking) {
            velocityX = 0;
            velocityY = 0;
            if (movingLeft)  velocityX -= speed;
            if (movingRight) velocityX += speed;
            if (movingUp)    velocityY += speed;
            if (movingDown)  velocityY -= speed;

            updatePosition(deltaTime);
        }

    }

    public void performAttack(AttackType attackType) {
        Attack attack = attacks.get(attackType);

        if (attack != null && attack.canUse() && !isAttacking) {
            attack.use();
            isAttacking = true;
            attackTimer = 0;
            currentAttackType = attackType;
        }
    }

    // Mulig at å håndtere hvilken vei spilleren er vendt mot er bedre håndtert med enum enn booleans?
    public boolean facingLeft() { return movingLeft; }
    public boolean facingRight() { return movingRight; }
    public boolean facingUp() { return movingUp; }
    public boolean facingDown() { return movingDown; }

    @Override
    public String getTextureName() {
        return "obligator.png";
    }

    public boolean isMovingLeft() { return movingLeft; }
    public boolean isMovingRight() { return movingRight; }
    public boolean isMovingUp() { return movingUp; }
    public boolean isMovingDown() { return movingDown; }

    public void setMovingLeft(boolean moving) { movingLeft = moving; }
    public void setMovingRight(boolean moving) { movingRight = moving; }
    public void setMovingUp(boolean moving) { movingUp = moving; }
    public void setMovingDown(boolean moving) { movingDown = moving; }

    @Override
    public boolean collidesWith(Collidable other) {
        return super.collidesWith(other);
    }
}
