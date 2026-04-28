package inf112.dungeoncrawler.model.entities;

public interface Targetable extends Collidable {
    boolean isInvincible();
    void applyKnockback(float vx, float vy);
    float getKnockBackForce();
}