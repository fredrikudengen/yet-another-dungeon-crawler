package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.combat.Attack;
import inf112.dungeoncrawler.model.entities.enemies.Enemy;
import inf112.dungeoncrawler.sound.SoundPlayer;
import inf112.dungeoncrawler.sound.SoundService;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;
import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public class Player extends Entity implements Movable{

    public static final float ATTACK_DURATION       = 0.2f;
    public static final float INVINCIBILITY_DURATION = 1.0f;

    private boolean movingLeft, movingRight, movingUp, movingDown;
    private Attack attack;
    private Direction facingDirection = Direction.RIGHT;
    private float attackTimer    = 0;
    private boolean isAttacking  = false;
    private boolean hasCheckedHits = false;

    private float invincibilityTimer = 0;
    private boolean isInvincible     = false;

    private SoundService sound;

    public Player(float x, float y, SoundService sound) {
        super(x, y);
        this.height = TILE_SIZE;
        this.width  = TILE_SIZE;
        this.speed  = 300;
        this.health = 5;
        this.damage = 1;
        this.attack = Attack.attack();
        this.sound  = sound;
        this.knockBackForce = 400;
    }

    @Override
    public void takeDamage(int damage, float sourceX, float sourceY) {
        if (!isInvincible) {
            super.takeDamage(damage, sourceX, sourceY);
            isInvincible       = true;
            invincibilityTimer = INVINCIBILITY_DURATION;
            sound.play(SoundPlayer.SoundEffect.DAMAGE_TAKEN);
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

        // --- Attack logic ---
        if (isAttacking) {
            attackTimer += (float) deltaTime;

            if (!hasCheckedHits) {
                checkAttackHits(world);
                hasCheckedHits = true;
            }

            if (attackTimer >= ATTACK_DURATION) {
                isAttacking    = false;
                attackTimer    = 0;
                hasCheckedHits = false;
            }
        }
        attack.update((float) deltaTime);
    }

    @Override
    public String getBaseTexturePath() {
        return "player/weapon/Player_walk_weapon_";
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    private void updateFacingDirection() {
        if      (movingLeft)  facingDirection = Direction.LEFT;
        else if (movingRight) facingDirection = Direction.RIGHT;
        else if (movingUp)    facingDirection = Direction.UP;
        else if (movingDown)  facingDirection = Direction.DOWN;
    }

    private void checkAttackHits(GameWorld world) {
        float range = attack.getRange();
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Enemy && entity != this) {
                if (isEnemyInAttackRange(entity, range)) {
                    entity.takeDamage(damage, x, y);
                }
            }
        }
    }

    private boolean isEnemyInAttackRange(Entity entity, float range) {
        float dx       = entity.getX() - x;
        float dy       = entity.getY() - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > range) return false;

        // TODO: find better/more natural attack area
        // Cone-shaped attack area
        switch (facingDirection) {
            case UP:    return dy > 0 && Math.abs(dx) < range / 2;
            case DOWN:  return dy < 0 && Math.abs(dx) < range / 2;
            case LEFT:  return dx < 0 && Math.abs(dy) < range / 2;
            case RIGHT: return dx > 0 && Math.abs(dy) < range / 2;
            default:    return false;
        }
    }

    public void performAttack(Direction direction) {
        if (attack != null && attack.canUse() && !isAttacking) {
            attack.use();
            isAttacking     = true;
            attackTimer     = 0;
            facingDirection = direction;
            hasCheckedHits  = false;
            sound.play(SoundPlayer.SoundEffect.SWORD_SWING);
        }
    }

    public float getKnockBackForce() {
        return knockBackForce;
    }

    public boolean isAttacking()     { return isAttacking;  }
    public float getAttackTimer()    { return attackTimer;  }

    @Override
    public boolean isMoving() {
        return movingLeft || movingRight || movingUp || movingDown;
    }

    @Override
    public Direction getFacingDirection() { return facingDirection; }
    public boolean isMovingLeft()  { return movingLeft;  }
    public boolean isMovingRight() { return movingRight; }
    public boolean isMovingUp()    { return movingUp;    }
    public boolean isMovingDown()  { return movingDown;  }

    public void setMovingLeft(boolean moving)  { movingLeft  = moving; }
    public void setMovingRight(boolean moving) { movingRight = moving; }
    public void setMovingUp(boolean moving)    { movingUp    = moving; }
    public void setMovingDown(boolean moving)  { movingDown  = moving; }

    @Override
    public boolean collidesWith(Collidable other) {
        return super.collidesWith(other);
    }
}