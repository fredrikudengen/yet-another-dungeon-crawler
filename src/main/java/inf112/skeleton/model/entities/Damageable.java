package inf112.skeleton.model.entities;

public interface Damageable {
    void takeDamage(int amount);
    int getCurrentHealth();
    int getMaxHealth();
    boolean isAlive();
}
