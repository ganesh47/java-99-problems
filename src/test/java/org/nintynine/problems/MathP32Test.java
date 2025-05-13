package org.nintynine.problems;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MathP32Test {

    @ParameterizedTest(name = "GCD of {0} and {1} should be {2}")
    @CsvSource({
            "36, 63, 9",     // Example from the problem statement
            "10, 5, 5",      // One number is multiple of another
            "7, 13, 1",      // Coprime numbers
            "48, 18, 6",     // Regular case
            "54, 24, 6",     // Another regular case
            "100, 10, 10",   // Multiple of 10
            "17, 17, 17",    // Same numbers
            "1, 1, 1",       // Both numbers are 1
            "98, 56, 14"     // Larger numbers
    })
    void testGcd(long a, long b, long expected) {
        assertEquals(expected, MathP32.gcd(a, b));
        assertEquals(expected, MathP32.gcd(b, a)); // Test commutative property

        // Test recursive implementation as well
        assertEquals(expected, MathP32.gcdRecursive(a, b));
        assertEquals(expected, MathP32.gcdRecursive(b, a));
    }

    @Test
    void testLargeNumbers() {
        assertEquals(3, MathP32.gcd(123456, 654321));
        assertEquals(3, MathP32.gcdRecursive(123456, 654321));
    }

    @Test
    void testNegativeNumbers() {
        assertThrows(IllegalArgumentException.class, () -> MathP32.gcd(-36, 63));
        assertThrows(IllegalArgumentException.class, () -> MathP32.gcd(36, -63));
        assertThrows(IllegalArgumentException.class, () -> MathP32.gcd(-36, -63));

        assertThrows(IllegalArgumentException.class, () -> MathP32.gcdRecursive(-36, 63));
        assertThrows(IllegalArgumentException.class, () -> MathP32.gcdRecursive(36, -63));
        assertThrows(IllegalArgumentException.class, () -> MathP32.gcdRecursive(-36, -63));
    }

    @Test
    void testZero() {
        assertThrows(IllegalArgumentException.class, () -> MathP32.gcd(0, 63));
        assertThrows(IllegalArgumentException.class, () -> MathP32.gcd(36, 0));
        assertThrows(IllegalArgumentException.class, () -> MathP32.gcd(0, 0));

        assertThrows(IllegalArgumentException.class, () -> MathP32.gcdRecursive(0, 63));
        assertEquals(36, MathP32.gcdRecursive(36, 0));
        assertThrows(IllegalArgumentException.class, () -> MathP32.gcdRecursive(0, 0));
    }
}
