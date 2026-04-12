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
    void canUse_initially_returnsTrue() {
        assertTrue(attack.canUse());
    }

    @Test
    void canUse_afterUse_returnsFalse() {
        attack.use();
        assertFalse(attack.canUse());
    }

    @Test
    void canUse_afterPartialUpdate_remainsFalse() {
        attack.use();
        attack.update(0.1f);
        assertFalse(attack.canUse());
    }

    @Test
    void canUse_afterFullCooldown_returnsTrue() {
        attack.use();
        attack.update(COOLDOWN);
        assertTrue(attack.canUse());
    }

    @Test
    void canUse_afterExcessiveUpdate_returnsTrue() {
        attack.use();
        attack.update(COOLDOWN * 10f);
        assertTrue(attack.canUse());
    }

    @Test
    void update_whenNotOnCooldown_remainsReady() {
        attack.update(COOLDOWN);
        assertTrue(attack.canUse());
    }

    @Test
    void use_thenUseAgainAfterCooldown_isAllowed() {
        attack.use();
        attack.update(COOLDOWN);
        attack.use();
        assertFalse(attack.canUse());
    }
}