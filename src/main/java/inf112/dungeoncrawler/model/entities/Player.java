package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.combat.Attack;
import inf112.dungeoncrawler.model.entities.enemies.Enemy;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;

import java.util.HashSet;
import java.util.Set;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public class Player extends Entity implements Movable, Controllable, Targetable {

    public static final float ATTACK_DURATION        = 0.2f;
    public static final float INVINCIBILITY_DURATION = 1.0f;
    private static final float ATTACK_REACH          = TILE_SIZE * 1.25f;
    private static final float ATTACK_SWEEP_PADDING  = TILE_SIZE * 0.25f;
    private static final float FRAME_DURATION        = 0.15f;

    private boolean movingLeft, movingRight, movingUp, movingDown;
    private final Attack attack;
    private Direction facingDirection = Direction.RIGHT;
    private float attackTimer      = 0;
    private boolean isAttacking    = false;
    private final Set<Entity> hitEnemiesThisSwing = new HashSet<>();

    private float invincibilityTimer = 0;
    private boolean isInvincible     = false;

    private final SoundService sound;

    public Player(float x, float y, SoundService sound) {
        super(x, y);
        this.height         = TILE_SIZE;
        this.width          = TILE_SIZE;
        this.speed          = 300;
        this.health         = 5;
        this.damage         = 1;
        this.attack         = new Attack();
        this.sound          = sound;
        this.knockBackForce = 400;
    }

    @Override
    public void takeDamage(int damage, float sourceX, float sourceY) {
        if (!isInvincible) {
            super.takeDamage(damage, sourceX, sourceY);
            isInvincible       = true;
            invincibilityTimer = INVINCIBILITY_DURATION;
            sound.play(SoundEffect.DAMAGE_TAKEN);
        }
    }

    @Override
    public void step(GameWorld world, double deltaTime) {
        if (isInvincible) {
            invincibilityTimer -= (float) deltaTime;
            if (invincibilityTimer <= 0) {
                isInvincible       = false;
                invincibilityTimer = 0;
            }
        }

        updateKnockback((float) deltaTime);

        velocityX = 0;
        velocityY = 0;
        if (movingLeft)  velocityX -= speed;
        if (movingRight) velocityX += speed;
        if (movingUp)    velocityY += speed;
        if (movingDown)  velocityY -= speed;

        if (!isAttacking) updateFacingDirection();

        Room room = world.getRoom();
        moveWithCollision(room,
                (velocityX + knockbackX) * (float) deltaTime,
                (velocityY + knockbackY) * (float) deltaTime);

        if (isAttacking) {
            attackTimer += (float) deltaTime;
            checkAttackHits(world);
            if (attackTimer >= ATTACK_DURATION) {
                isAttacking = false;
                attackTimer = 0;
                hitEnemiesThisSwing.clear();
            }
        }
        attack.update((float) deltaTime);
    }

    @Override
    public float getFrameDuration() {
        return FRAME_DURATION;
    }

    @Override
    public boolean isInvincible() { return isInvincible; }

    @Override
    public float getKnockBackForce() { return knockBackForce; }

    private void updateFacingDirection() {
        if      (movingLeft)  facingDirection = Direction.LEFT;
        else if (movingRight) facingDirection = Direction.RIGHT;
        else if (movingUp)    facingDirection = Direction.UP;
        else if (movingDown)  facingDirection = Direction.DOWN;
    }

    private void checkAttackHits(GameWorld world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Enemy
                    && !hitEnemiesThisSwing.contains(entity)
                    && isEnemyInAttackRange(entity)) {
                entity.takeDamage(damage, x, y);
                hitEnemiesThisSwing.add(entity);
            }
        }
    }

    private boolean isEnemyInAttackRange(Entity entity) {
        float hitboxX      = x;
        float hitboxY      = y;
        float hitboxWidth  = width;
        float hitboxHeight = height;

        switch (facingDirection) {
            case LEFT -> {
                hitboxX      = x - ATTACK_REACH;
                hitboxY      = y - ATTACK_SWEEP_PADDING;
                hitboxWidth  = ATTACK_REACH;
                hitboxHeight = height + ATTACK_SWEEP_PADDING * 2f;
            }
            case RIGHT -> {
                hitboxX      = x + width;
                hitboxY      = y - ATTACK_SWEEP_PADDING;
                hitboxWidth  = ATTACK_REACH;
                hitboxHeight = height + ATTACK_SWEEP_PADDING * 2f;
            }
            case UP -> {
                hitboxX      = x - ATTACK_SWEEP_PADDING;
                hitboxY      = y + height;
                hitboxWidth  = width + ATTACK_SWEEP_PADDING * 2f;
                hitboxHeight = ATTACK_REACH;
            }
            case DOWN -> {
                hitboxX      = x - ATTACK_SWEEP_PADDING;
                hitboxY      = y - ATTACK_REACH;
                hitboxWidth  = width + ATTACK_SWEEP_PADDING * 2f;
                hitboxHeight = ATTACK_REACH;
            }
        }

        return Collidable.overlaps(
                hitboxX, hitboxY, hitboxWidth, hitboxHeight,
                entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
    }

    @Override
    public void performAttack(Direction direction) {
        if (attack.canUse() && !isAttacking) {
            attack.use();
            isAttacking     = true;
            attackTimer     = 0;
            facingDirection = direction;
            hitEnemiesThisSwing.clear();
            sound.play(SoundEffect.SWORD_SWING);
        }
    }

    @Override public boolean isAttacking()   { return isAttacking; }

    @Override
    public int getAttackFrame() {
        return (attackTimer < ATTACK_DURATION / 2f) ? 1 : 2;
    }

    @Override public boolean isMoving() {
        return movingLeft || movingRight || movingUp || movingDown;
    }

    @Override public Direction getFacingDirection() { return facingDirection; }

    @Override public void setMovingLeft(boolean moving)  { movingLeft  = moving; }
    @Override public void setMovingRight(boolean moving) { movingRight = moving; }
    @Override public void setMovingUp(boolean moving)    { movingUp    = moving; }
    @Override public void setMovingDown(boolean moving)  { movingDown  = moving; }
}