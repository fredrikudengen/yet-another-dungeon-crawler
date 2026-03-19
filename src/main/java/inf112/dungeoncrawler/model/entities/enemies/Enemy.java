package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Player;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public abstract class Enemy extends Entity {

    protected Player player;

    private static final int   TILE_SIZE          = 32;
    private static final float PATH_REFRESH_RATE   = 0.5f;
    private static final float WAYPOINT_RADIUS     = TILE_SIZE * 0.6f;

    private static final float DETECTION_RADIUS    = TILE_SIZE * 12f;

    private static final float PLAYER_KNOCKBACK_FORCE = 400f;
    private static final float ENEMY_KNOCKBACK_FORCE  = 350f;
    private static final float KNOCKBACK_FRICTION     = 800f;

    private List<int[]> path    = new ArrayList<>();
    private float pathTimer     = PATH_REFRESH_RATE;
    private boolean isChasing   = false;
    private float stunTimer     = 0f;
    private float knockbackX    = 0f;
    private float knockbackY    = 0f;

    public Enemy(float x, float y, Player player) {
        super(x, y);
        this.player = player;
    }

    @Override
    public void step(GameWorld world, double deltaTime) {
        if (this.collidesWith(player) && !player.isInvincible()) {
            applyKnockbackToPlayer();
            stunTimer = Player.INVINCIBILITY_DURATION;
            this.onCollision(player);
        }

        Room room = world.getRoom();

        if (knockbackX != 0 || knockbackY != 0) {
            float kbSpeed   = (float) Math.sqrt(knockbackX * knockbackX + knockbackY * knockbackY);
            float reduction = KNOCKBACK_FRICTION * (float) deltaTime;
            if (reduction >= kbSpeed) {
                knockbackX = 0;
                knockbackY = 0;
            } else {
                float factor = (kbSpeed - reduction) / kbSpeed;
                knockbackX *= factor;
                knockbackY *= factor;
            }
        }

        if (knockbackX != 0 || knockbackY != 0) {
            float kbNewX = x + knockbackX * (float) deltaTime;
            float kbNewY = y + knockbackY * (float) deltaTime;
            if (!room.isBlocked(kbNewX, kbNewY, width, height, TILE_SIZE)) {
                setPosition(kbNewX, kbNewY);
            } else if (!room.isBlocked(kbNewX, y, width, height, TILE_SIZE)) {
                setPosition(kbNewX, y);
            } else if (!room.isBlocked(x, kbNewY, width, height, TILE_SIZE)) {
                setPosition(x, kbNewY);
            }
        }

        if (stunTimer > 0) {
            stunTimer -= (float) deltaTime;
            velocityX  = 0;
            velocityY  = 0;
            return;
        }

        float pdx = player.getX() - x;
        float pdy = player.getY() - y;
        float distToPlayer = (float) Math.sqrt(pdx * pdx + pdy * pdy);

        if (!isChasing && distToPlayer <= DETECTION_RADIUS) {
            isChasing = true;
        } else if (isChasing && distToPlayer > DETECTION_RADIUS) {
            isChasing = false;
            path.clear();
        }

        if (!isChasing) {
            velocityX = 0;
            velocityY = 0;
            return;
        }

        pathTimer += (float) deltaTime;
        if (pathTimer >= PATH_REFRESH_RATE) {
            pathTimer = 0f;
            path = bfsPath(room);
        }

        while (!path.isEmpty()) {
            int[] next = path.getFirst();
            float wpX = next[0] * TILE_SIZE + TILE_SIZE / 2f;
            float wpY = next[1] * TILE_SIZE + TILE_SIZE / 2f;
            float ddx = wpX - x;
            float ddy = wpY - y;
            if (ddx * ddx + ddy * ddy < WAYPOINT_RADIUS * WAYPOINT_RADIUS) {
                path.removeFirst();
            } else {
                break;
            }
        }

        float targetX, targetY;
        if (!path.isEmpty()) {
            int[] next = path.getFirst();
            targetX = next[0] * TILE_SIZE + TILE_SIZE / 2f;
            targetY = next[1] * TILE_SIZE + TILE_SIZE / 2f;
        } else {
            targetX = player.getX();
            targetY = player.getY();
        }

        float dx = targetX - x;
        float dy = targetY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            velocityX = (dx / distance) * speed;
            velocityY = (dy / distance) * speed;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        float newX = x + velocityX * (float) deltaTime;
        float newY = y + velocityY * (float) deltaTime;

        if (!room.isBlocked(newX, newY, width, height, TILE_SIZE)) {
            setPosition(newX, newY);
        } else if (!room.isBlocked(newX, y, width, height, TILE_SIZE)) {
            setPosition(newX, y);
        } else if (!room.isBlocked(x, newY, width, height, TILE_SIZE)) {
            setPosition(x, newY);
        }
    }

    private void applyKnockbackToPlayer() {
        float dx   = player.getX() - x;
        float dy   = player.getY() - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        if (dist > 0) {
            player.applyKnockback((dx / dist) * PLAYER_KNOCKBACK_FORCE,
                    (dy / dist) * PLAYER_KNOCKBACK_FORCE);
        }
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        float dx   = x - player.getX();
        float dy   = y - player.getY();
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        if (dist > 0) {
            knockbackX = (dx / dist) * ENEMY_KNOCKBACK_FORCE;
            knockbackY = (dy / dist) * ENEMY_KNOCKBACK_FORCE;
        }
        stunTimer = Player.INVINCIBILITY_DURATION;
    }

    private List<int[]> bfsPath(Room room) {
        int startX = (int) (x / TILE_SIZE);
        int startY = (int) (y / TILE_SIZE);
        int goalX  = (int) (player.getX() / TILE_SIZE);
        int goalY  = (int) (player.getY() / TILE_SIZE);

        if (startX == goalX && startY == goalY) {
            return new ArrayList<>();
        }

        Queue<int[]> open            = new LinkedList<>();
        Map<String, String> cameFrom = new HashMap<>();
        // tile coordinates as keys ("x,y")
        String startKey = startX + "," + startY;
        String goalKey  = goalX  + "," + goalY;

        open.add(new int[]{ startX, startY });
        cameFrom.put(startKey, null);

        int[][] neighbours = { {1,0}, {-1,0}, {0,1}, {0,-1} };

        while (!open.isEmpty()) {
            int[] current = open.poll();
            String currentKey = current[0] + "," + current[1];

            if (currentKey.equals(goalKey)) {
                return reconstructPath(cameFrom, startKey, goalKey);
            }

            for (int[] dir : neighbours) {
                int nx = current[0] + dir[0];
                int ny = current[1] + dir[1];
                String nKey = nx + "," + ny;

                if (cameFrom.containsKey(nKey)) continue;

                float worldX = nx * TILE_SIZE + TILE_SIZE / 2f;
                float worldY = ny * TILE_SIZE + TILE_SIZE / 2f;
                if (room.isBlocked(worldX, worldY, width, height, TILE_SIZE)) continue;

                cameFrom.put(nKey, currentKey);
                open.add(new int[]{ nx, ny });
            }
        }
        return new ArrayList<>();
    }

    private List<int[]> reconstructPath(Map<String, String> cameFrom,
                                        String startKey, String goalKey) {
        List<int[]> result = new ArrayList<>();
        String current = goalKey;

        while (current != null && !current.equals(startKey)) {
            String[] parts = current.split(",");
            result.add(new int[]{ Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]) });
            current = cameFrom.get(current);
        }
        Collections.reverse(result);
        return result;
    }

    @Override
    public abstract void onCollision(Entity entity);

    public abstract String getBaseTexturePath();

    public Direction getFacingDirection() {
        if (Math.abs(velocityX) >= Math.abs(velocityY)) {
            return (velocityX >= 0) ? Direction.RIGHT : Direction.LEFT;
        }
        return (velocityY >= 0) ? Direction.UP : Direction.DOWN;
    }

    public boolean isMoving() {
        return velocityX != 0 || velocityY != 0;
    }

    public boolean isChasing() {
        return isChasing;
    }
}