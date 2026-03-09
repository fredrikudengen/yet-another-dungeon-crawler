package inf112.skeleton.model.combat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttackTest {

    private Attack attack;

    @BeforeEach
    void setUp() {
        attack = new Attack(10, 2.0f, 50f, 5f);
    }


    @Test
    void testCanUseInitially() {
        assertTrue(attack.canUse());
    }

    @Test
    void testCooldownProgressInitially() {
        assertEquals(0.0f, attack.getCooldownProgress(), 0.001f);
    }


    @Test
    void testCannotUseAfterUsing() {
        attack.use();
        assertFalse(attack.canUse());
    }

    @Test
    void testCooldownProgressAfterUse() {
        attack.use();
        assertEquals(1.0f, attack.getCooldownProgress(), 0.001f);
    }

    @Test
    void testUseIgnoredWhenOnCooldown() {
        attack.use();
        // progress should still be 1.0 – use() must not reset the cooldown a second time
        float progressAfterFirstUse = attack.getCooldownProgress();
        attack.use(); // should be a no-op
        assertEquals(progressAfterFirstUse, attack.getCooldownProgress(), 0.001f);
    }


    @Test
    void testUpdateReducesCooldown() {
        attack.use();
        attack.update(1.0f);
        // cooldown was 2.0, now 1.0 → progress = 0.5
        assertEquals(0.5f, attack.getCooldownProgress(), 0.001f);
    }

    @Test
    void testCanUseAfterFullCooldown() {
        attack.use();
        attack.update(2.0f);
        assertTrue(attack.canUse());
    }

    @Test
    void testUpdateDoesNotGoBelowZero() {
        attack.use();
        attack.update(10.0f); // way more than the 2.0s cooldown
        assertTrue(attack.canUse());
        assertEquals(0.0f, attack.getCooldownProgress(), 0.001f);
    }

    @Test
    void testUpdateDoesNothing_WhenNotOnCooldown() {
        // cooldown already 0, update should have no effect
        attack.update(1.0f);
        assertTrue(attack.canUse());
        assertEquals(0.0f, attack.getCooldownProgress(), 0.001f);
    }


    @Test
    void testGetDamage() {
        assertEquals(1, attack.getDamage());
    }

    @Test
    void testGetRange() {
        assertEquals(50f, attack.getRange(), 0.001f);
    }

    @Test
    void testGetKnockback() {
        assertEquals(5f, attack.getKnockback(), 0.001f);
    }


    @Test
    void testAttackFactory() {
        Attack basic = Attack.attack();
        assertEquals(1, basic.getDamage());
        assertEquals(50f, basic.getRange(), 0.001f);
        assertEquals(5f, basic.getKnockback(), 0.001f);
        assertTrue(basic.canUse());
    }


}
