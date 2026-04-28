package inf112.dungeoncrawler.model.entities.enemies;

import inf112.dungeoncrawler.model.Direction;
import inf112.dungeoncrawler.model.entities.Entity;
import inf112.dungeoncrawler.model.entities.Targetable;
import inf112.dungeoncrawler.model.entities.Movable;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.model.world.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static inf112.dungeoncrawler.util.Constants.TILE_SIZE;

public abstract class Enemy extends Entity implements Movable {

    private static final float STUN_DURATION     = 1.0f;
    private static final float PATH_REFRESH_RATE = 0.5f;
    private static final float WAYPOINT_RADIUS   = TILE_SIZE * 0.6f;
    private static final float DETECTION_RADIUS  = TILE_SIZE * 12f;

    private static final int[][] DIRECTIONS = { {1,0}, {-1,0}, {0,1}, {0,-1} };

    private final Targetable target;

    private List<int[]> path = new ArrayList<>();
    private float pathTimer  = PATH_REFRESH_RATE;
    private float stunTimer  = 0f;
    private boolean moving   = false;

    public Enemy(float x, float y, Targetable target) {
        super(x, y);
        this.target = target;
        this.width  = TILE_SIZE;
        this.height = TILE_SIZE;
    }

    @Override
    public void step(GameWorld world, double deltaTime) {
        Room room = world.getRoom();

        updateKnockback((float) deltaTime);
        moveWithCollision(room, knockbackX * (float) deltaTime, knockbackY * (float) deltaTime);

        if (stunTimer > 0) {
            stunTimer -= (float) deltaTime;
            velocityX  = 0;
            velocityY  = 0;
            return;
        }

        if (this.collidesWith(target) && !target.isInvincible()) {
            applyKnockbackToTarget();
            stunTimer = STUN_DURATION;
            this.onCollision((Entity) target);
        }

        float pdx          = target.getX() - x;
        float pdy          = target.getY() - y;
        float distToTarget = (float) Math.sqrt(pdx * pdx + pdy * pdy);

        if (!moving && distToTarget <= DETECTION_RADIUS) {
            moving = true;
        } else if (moving && distToTarget > DETECTION_RADIUS) {
            moving = false;
            path.clear();
        }

        if (!moving) {
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
            float wpX  = tileCenterX(next);
            float wpY  = tileCenterY(next);
            float ddx  = wpX - x;
            float ddy  = wpY - y;
            if (ddx * ddx + ddy * ddy < WAYPOINT_RADIUS * WAYPOINT_RADIUS) {
                path.removeFirst();
            } else {
                break;
            }
        }

        float targetX = path.isEmpty() ? target.getX() : tileCenterX(path.getFirst());
        float targetY = path.isEmpty() ? target.getY() : tileCenterY(path.getFirst());

        float dx       = targetX - x;
        float dy       = targetY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            velocityX = (dx / distance) * speed;
            velocityY = (dy / distance) * speed;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        moveWithCollision(room, velocityX * (float) deltaTime, velocityY * (float) deltaTime);
    }

    @Override
    public void takeDamage(int damage, float sourceX, float sourceY) {
        super.takeDamage(damage, sourceX, sourceY);
        stunTimer = STUN_DURATION;
    }

    @Override
    public void onCollision(Entity entity) {
        entity.takeDamage(this.damage, this.x, this.y);
    }

    private void applyKnockbackToTarget() {
        float dx   = target.getX() - x;
        float dy   = target.getY() - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        if (dist > 0) {
            target.applyKnockback(
                    (dx / dist) * target.getKnockBackForce(),
                    (dy / dist) * target.getKnockBackForce());
        }
    }

    private List<int[]> bfsPath(Room room) {
        int startX = (int) (x / TILE_SIZE);
        int startY = (int) (y / TILE_SIZE);
        int goalX  = (int) (target.getX() / TILE_SIZE);
        int goalY  = (int) (target.getY() / TILE_SIZE);

        if (startX == goalX && startY == goalY) return new ArrayList<>();

        Queue<int[]>    open     = new LinkedList<>();
        Map<Long, Long> cameFrom = new HashMap<>();
        long startKey = tileKey(startX, startY);
        long goalKey  = tileKey(goalX, goalY);

        open.add(new int[]{ startX, startY });
        cameFrom.put(startKey, null);

        while (!open.isEmpty()) {
            int[] current    = open.poll();
            long  currentKey = tileKey(current[0], current[1]);

            if (currentKey == goalKey) {
                return reconstructPath(cameFrom, startKey, goalKey);
            }

            for (int[] dir : DIRECTIONS) {
                int  nx   = current[0] + dir[0];
                int  ny   = current[1] + dir[1];
                long nKey = tileKey(nx, ny);

                if (cameFrom.containsKey(nKey)) continue;

                float worldX = nx * TILE_SIZE + TILE_SIZE / 2f;
                float worldY = ny * TILE_SIZE + TILE_SIZE / 2f;
                if (room.isBlocked(worldX, worldY, width, height)) continue;

                cameFrom.put(nKey, currentKey);
                open.add(new int[]{ nx, ny });
            }
        }
        return new ArrayList<>();
    }

    private List<int[]> reconstructPath(Map<Long, Long> cameFrom,
                                        long startKey, long goalKey) {
        List<int[]> result = new ArrayList<>();
        Long current = goalKey;
        while (current != null && current != startKey) {
            result.add(new int[]{ (int) (current >> 32), (int) (current.longValue()) });
            current = cameFrom.get(current);
        }
        Collections.reverse(result);
        return result;
    }

    private static long tileKey(int x, int y) {
        return ((long) x << 32) | (y & 0xFFFFFFFFL);
    }

    private static float tileCenterX(int[] tile) { return tile[0] * TILE_SIZE + TILE_SIZE / 2f; }
    private static float tileCenterY(int[] tile) { return tile[1] * TILE_SIZE + TILE_SIZE / 2f; }

    @Override
    public abstract String getBaseTexturePath();

    @Override
    public boolean isMoving() { return moving; }

    @Override
    public Direction getFacingDirection() {
        if (Math.abs(velocityX) >= Math.abs(velocityY)) {
            return (velocityX >= 0) ? Direction.RIGHT : Direction.LEFT;
        }
        return (velocityY >= 0) ? Direction.UP : Direction.DOWN;
    }
}