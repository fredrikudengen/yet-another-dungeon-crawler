package inf112.skeleton.model.combat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttackTest {

    private Attack attack;

    @BeforeEach
    void setUp() {
        attack = new Attack(AttackType.BASIC, 10, 2.0f, 50f, 5f);
    }

    @Test
    void testCanUseInitially() {
        assertTrue(attack.canUse());
    }
}