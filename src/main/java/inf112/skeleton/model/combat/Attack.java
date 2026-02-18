package inf112.skeleton.model.combat;

public class Attack {
    private final AttackType type;
    private final int damage;
    private final float cooldown; // Seconds
    private final float range;
    private final float knockback;
    private float currentCooldown;

    public Attack(AttackType type, int damage, float cooldown, float range, float knockback) {
        this.type = type;
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
        this.knockback = knockback;
    }

    public static Attack basicAttack() {
        return new Attack(AttackType.BASIC, 10, 0.5f, 50f, 5f);
    }

    public static Attack heavyAttack() {
        return new Attack(AttackType.HEAVY, 20, 2f, 50f, 10f);
    }

    public static Attack powerupAttack() {
        return new Attack(AttackType.POWERUP, 40, 5f, 75f, 25f);
    }

    public void update(float deltaTime) {
        if (currentCooldown > 0) {
            currentCooldown -= deltaTime;
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

    public AttackType getType() { return type; }
    public int getDamage() { return damage; }
    public float getRange() { return range; }
    public float getKnockback() { return knockback; }
    public float getCooldownProgress() { return currentCooldown / cooldown; }

}
