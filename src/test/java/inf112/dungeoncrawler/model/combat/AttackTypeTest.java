// package inf112.skeleton.model.combat;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;

// class AttackTypeTest {

//     @Test
//     void testAllThreeTypesExist() {
//         AttackType[] types = AttackType.values();
//         assertEquals(3, types.length);
//     }

//     @Test
//     void testBasicTypeExists() {
//         assertEquals(AttackType.BASIC, AttackType.valueOf("BASIC"));
//     }

//     @Test
//     void testHeavyTypeExists() {
//         assertEquals(AttackType.HEAVY, AttackType.valueOf("HEAVY"));
//     }

//     @Test
//     void testPowerupTypeExists() {
//         assertEquals(AttackType.POWERUP, AttackType.valueOf("POWERUP"));
//     }

//     @Test
//     void testTypesAreDistinct() {
//         assertNotEquals(AttackType.BASIC, AttackType.HEAVY);
//         assertNotEquals(AttackType.BASIC, AttackType.POWERUP);
//         assertNotEquals(AttackType.HEAVY, AttackType.POWERUP);
//     }

//     @Test
//     void testValueOfUnknownThrows() {
//         assertThrows(IllegalArgumentException.class, () -> AttackType.valueOf("UNKNOWN"));
//     }
// }
