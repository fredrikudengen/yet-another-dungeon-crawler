package inf112.skeleton.model.combat;

public class Attack {
    private final int damage;
    private final float cooldown;
    private final float range;
    private final float knockback;
    private float currentCooldown;

    public Attack(int damage, float cooldown, float range, float knockback) {
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
        this.knockback = knockback;
    }

    public static Attack attack() {
        return new Attack(1, 1f, 100f, 5f);
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

    public int getDamage() { return damage; }
    public float getRange() { return range; }
    public float getKnockback() { return knockback; }
    public float getCooldownProgress() { return currentCooldown / cooldown; }

}
