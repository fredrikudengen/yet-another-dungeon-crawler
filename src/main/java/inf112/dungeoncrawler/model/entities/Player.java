package inf112.dungeoncrawler.model.entities;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.combat.Attack;
import inf112.dungeoncrawler.model.entities.enemies.Enemy;
import inf112.dungeoncrawler.sound.SoundEffect;
import inf112.dungeoncrawler.sound.SoundService;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public class Player extends Entity implements Movable, Controllable {

    public static final float ATTACK_DURATION        = 0.2f;
    public static final float INVINCIBILITY_DURATION = 1.0f;

    private boolean movingLeft, movingRight, movingUp, movingDown;
    private final Attack attack;
    private Direction facingDirection = Direction.RIGHT;
    private float attackTimer      = 0;
    private boolean isAttacking    = false;
    private boolean hasCheckedHits = false;

    private float invincibilityTimer = 0;
    private boolean isInvincible     = false;

    private final SoundService sound;

    /**
     * Player object which the user controls.
     *
     * @param x pixel value
     * @param y pixel value
     * @param sound injection used to play sound
     */
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

    public boolean isInvincible() { return isInvincible; }

    private void updateFacingDirection() {
        if      (movingLeft)  facingDirection = Direction.LEFT;
        else if (movingRight) facingDirection = Direction.RIGHT;
        else if (movingUp)    facingDirection = Direction.UP;
        else if (movingDown)  facingDirection = Direction.DOWN;
    }

    private void checkAttackHits(GameWorld world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Enemy && isEnemyInAttackRange(entity)) {
                entity.takeDamage(damage, x, y);
            }
        }
    }

    private boolean isEnemyInAttackRange(Entity entity) {
        int playerTileX = (int) (x / TILE_SIZE);
        int playerTileY = (int) (y / TILE_SIZE);
        int entityTileX = (int) (entity.getX() / TILE_SIZE);
        int entityTileY = (int) (entity.getY() / TILE_SIZE);

        int dx = entityTileX - playerTileX;
        int dy = entityTileY - playerTileY;

        return switch (facingDirection) {
            case LEFT  -> dx == -1 && Math.abs(dy) <= 1;
            case RIGHT -> dx == 1  && Math.abs(dy) <= 1;
            case UP    -> dy == 1  && Math.abs(dx) <= 1;
            case DOWN  -> dy == -1 && Math.abs(dx) <= 1;
        };
    }

    @Override
    public void performAttack(Direction direction) {
        if (attack.canUse() && !isAttacking) {
            attack.use();
            isAttacking     = true;
            attackTimer     = 0;
            facingDirection = direction;
            hasCheckedHits  = false;
            sound.play(SoundEffect.SWORD_SWING);
        }
    }

    @Override
    public boolean isAttacking() { return isAttacking; }

    @Override
    public int getAttackFrame() {
        return (attackTimer < ATTACK_DURATION / 2f) ? 1 : 2;
    }

    public float getKnockBackForce() { return knockBackForce; }

    @Override
    public boolean isMoving() {
        return movingLeft || movingRight || movingUp || movingDown;
    }

    @Override
    public Direction getFacingDirection() { return facingDirection; }

    @Override public void setMovingLeft(boolean moving)  { movingLeft  = moving; }
    @Override public void setMovingRight(boolean moving) { movingRight = moving; }
    @Override public void setMovingUp(boolean moving)    { movingUp    = moving; }
    @Override public void setMovingDown(boolean moving)  { movingDown  = moving; }

    @Override
    public boolean collidesWith(Collidable other) {
        return super.collidesWith(other);
    }
}