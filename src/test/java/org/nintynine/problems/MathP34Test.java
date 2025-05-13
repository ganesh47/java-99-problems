package org.nintynine.problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MathP34Test {

    @ParameterizedTest(name = "φ({0}) = {1}")
    @DisplayName("Test known totient values")
    @CsvSource({
            "1, 1",      // Special case
            "10, 4",     // Example from problem statement (coprime: 1,3,7,9)
            "2, 1",      // Smallest prime
            "3, 2",      // Prime
            "4, 2",      // Power of 2
            "7, 6",      // Prime
            "8, 4",      // Power of 2
            "9, 6",      // Perfect square
            "12, 4",     // Composite
            "15, 8",     // Product of two primes
            "16, 8",     // Power of 2
            "20, 8",     // Composite
            "25, 20",    // Perfect square of prime
            "30, 8"      // Highly composite
    })
    void testTotientPhi(long input, long expected) {
        assertEquals(expected, MathP34.totientPhi(input));
    }

    @Test
    @DisplayName("Test prime number totient properties")
    void testPrimeNumberTotient() {
        // For any prime number p, φ(p) = p-1
        long[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
        for (long prime : primes) {
            assertEquals(prime - 1, MathP34.totientPhi(prime),
                    "For prime number " + prime + ", φ(p) should be p-1");
        }
    }

    @Test
    @DisplayName("Test totient properties for composite numbers")
    void testCompositeNumberProperties() {
        // Test multiplicative property for coprime numbers
        // If a and b are coprime, then φ(a*b) = φ(a) * φ(b)
        assertEquals(MathP34.totientPhi(15),
                MathP34.totientPhi(3) * MathP34.totientPhi(5),
                "φ(15) should equal φ(3) * φ(5)");

        // Test for perfect squares
        assertEquals(6, MathP34.totientPhi(9));  // 9 = 3²
        assertEquals(8, MathP34.totientPhi(16)); // 16 = 2⁴
    }

    @Test
    @DisplayName("Test prime and totient check")
    void testPrimeAndTotient() {
        // Test prime number
        var result1 = MathP34.checkPrimeAndTotient(17);
        assertTrue(result1.isPrime());
        assertEquals(16, result1.totientValue());

        // Test composite number
        var result2 = MathP34.checkPrimeAndTotient(24);
        assertFalse(result2.isPrime());
        assertEquals(8, result2.totientValue());
    }

    @Test
    @DisplayName("Test invalid inputs")
    void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> MathP34.totientPhi(0));
        assertThrows(IllegalArgumentException.class, () -> MathP34.totientPhi(-1));
        assertThrows(IllegalArgumentException.class, () -> MathP34.totientPhi(-100));
    }

    @Test
    @DisplayName("Test larger numbers")
    void testLargerNumbers() {
        // Test a larger prime number
        assertEquals(96, MathP34.totientPhi(97)); // 97 is prime, so φ(97) = 96

        // Test a larger composite number
        assertEquals(40, MathP34.totientPhi(100)); // 100 = 2² * 5²
    }
}
