package inf112.dungeoncrawler.model.combat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttackTest {

    private static final float COOLDOWN = 2.0f;

    private Attack attack;

    @BeforeEach
    void setUp() {
        attack = new Attack();
    }

    @Test
    void canUseInitiallyReturnsTrue() {
        assertTrue(attack.canUse());
    }

    @Test
    void canUseAfterUseReturnsFalse() {
        attack.use();
        assertFalse(attack.canUse());
    }

    @Test
    void canUseRemainsFalse() {
        attack.use();
        attack.update(0.1f);
        assertFalse(attack.canUse());
    }

    @Test
    void canUseAfterCooldownReturnsTrue() {
        attack.use();
        attack.update(COOLDOWN);
        assertTrue(attack.canUse());
    }

    @Test
    void canUseAfterLongCooldownReturnsTrue() {
        attack.use();
        attack.update(COOLDOWN * 10f);
        assertTrue(attack.canUse());
    }

    @Test
    void updateReturnsTrue() {
        attack.update(COOLDOWN);
        assertTrue(attack.canUse());
    }

    @Test
    void useThenUseAfterCooldownReturnsTrue() {
        attack.use();
        attack.update(COOLDOWN);
        attack.use();
        assertFalse(attack.canUse());
    }
}