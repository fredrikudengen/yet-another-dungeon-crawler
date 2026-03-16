package inf112.dungeoncrawler.model.combat;

public class Attack {
    private final float cooldown;
    private final float range;
    private final float knockback;
    private float currentCooldown;

    public Attack(float cooldown, float range, float knockback) {
        this.cooldown = cooldown;
        this.range = range;
        this.knockback = knockback;
    }

    public static Attack attack() {
        return new Attack(1f, 100f, 5f);
    }

    public void update(float deltaTime) {
        if (currentCooldown > 0) {
            currentCooldown = Math.max(0, currentCooldown - deltaTime);
        }
    }

    public boolean canUse() {
        return currentCooldown <= 0;
    }

    public void use() {
        if (canUse()) {
            currentCooldown = cooldown;
        }
    }

    public float getRange() { return range; }
    public float getKnockback() { return knockback; }
    public float getCooldownProgress() { return currentCooldown / cooldown; }

}
