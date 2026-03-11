package inf112.skeleton.model.combat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttackTest {

    private Attack attack;

    @BeforeEach
    void setUp() {
        attack = new Attack(2.0f, 50f, 5f);  
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
        float progressAfterFirstUse = attack.getCooldownProgress();
        attack.use(); 
        assertEquals(progressAfterFirstUse, attack.getCooldownProgress(), 0.001f);
    }

    @Test
    void testUpdateReducesCooldown() {
        attack.use();
        attack.update(1.0f);
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
        attack.update(10.0f); 
        assertTrue(attack.canUse());
        assertEquals(0.0f, attack.getCooldownProgress(), 0.001f);
    }

    @Test
    void testUpdateDoesNothing_WhenNotOnCooldown() {
        attack.update(1.0f);
        assertTrue(attack.canUse());
        assertEquals(0.0f, attack.getCooldownProgress(), 0.001f);
    }

    @Test
    void testGetRange() {
        assertEquals(50f, attack.getRange(), 0.001f);
    }

    @Test
    void testGetKnockback() {
        assertEquals(5f, attack.getKnockback(), 0.001f);
    }

}