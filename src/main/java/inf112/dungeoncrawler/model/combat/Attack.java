package inf112.dungeoncrawler.model.combat;

public class Attack {

    private final float cooldown;
    private float currentCooldown = 0f;

    /**
     * Attack used by Player.
     */
    public Attack() {
        this.cooldown = 1f;
    }

    /**
     * Updates the cooldown for attack.
     *
     * @param deltaTime time elapsed since last update.
     */
    public void update(float deltaTime) {
        currentCooldown = Math.max(0, currentCooldown - deltaTime);
    }

    /**
     * Returns true if cooldown is 0.
     */
    public boolean canUse() {
        return currentCooldown <= 0;
    }

    /**
     * Sets cooldown to new timer.
     */
    public void use() {
        currentCooldown = cooldown;
    }
}
