package inf112.dungeoncrawler.model.combat;

public class Attack {
    private final float cooldown;
    private final float knockback;
    private float currentCooldown;

    public Attack(float cooldown,float knockback) {
        this.cooldown = cooldown;
        this.knockback = knockback;
    }

    public static Attack attack() {
        return new Attack(1f, 5f);
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

    public float getKnockback() { return knockback; }
    public float getCooldownProgress() { return currentCooldown / cooldown; }

}
