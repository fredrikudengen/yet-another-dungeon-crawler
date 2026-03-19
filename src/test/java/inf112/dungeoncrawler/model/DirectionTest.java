package inf112.dungeoncrawler.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DirectionTest {

	@Test
	void testIsVerticalIsTrueForUpAndDown() {
		assertTrue(Direction.UP.isVertical());
		assertTrue(Direction.DOWN.isVertical());
	}

	@Test
	void testIsVerticalIsFalseForLeftAndRight() {
		assertFalse(Direction.LEFT.isVertical());
		assertFalse(Direction.RIGHT.isVertical());
	}

	@Test
	void testIsHorizontalIsTrueForLeftAndRight() {
		assertTrue(Direction.LEFT.isHorizontal());
		assertTrue(Direction.RIGHT.isHorizontal());
	}

	@Test
	void testIsHorizontalIsFalseForUpAndDown() {
		assertFalse(Direction.UP.isHorizontal());
		assertFalse(Direction.DOWN.isHorizontal());
	}

	@Test
	void testDirectionClassificationIsMutuallyExclusive() {
		for (Direction direction : Direction.values()) {
			assertNotEquals(direction.isVertical(), direction.isHorizontal());
		}
	}
}
