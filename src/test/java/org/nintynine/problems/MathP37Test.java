package org.nintynine.problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MathP37Test {

    @ParameterizedTest(name = "φ({0}) = {1}")
    @DisplayName("Test known totient values")
    @CsvSource({
            "1, 1",      // Special case
            "2, 1",      // Prime
            "3, 2",      // Prime
            "4, 2",      // Power of 2
            "6, 2",      // Product of primes
            "8, 4",      // Power of 2
            "9, 6",      // Perfect square
            "10, 4",     // Product of primes
            "12, 4",     // Multiple factors
            "15, 8",     // Product of primes
            "16, 8",     // Power of 2
            "20, 8",     // Multiple factors
            "25, 20",    // Power of prime
            "30, 8",     // Multiple factors
            "315, 144",  // Example from P34
            "1024, 512"  // Large power of 2
    })
    void testTotientPhi(long input, long expected) {
        assertEquals(expected, MathP37.totientPhi(input));
    }

    @Test
    @DisplayName("Test invalid inputs")
    void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> MathP37.totientPhi(0));
        assertThrows(IllegalArgumentException.class, () -> MathP37.totientPhi(-1));
        assertThrows(IllegalArgumentException.class, () -> MathP37.totientPhi(-315));
    }

    @Test
    @DisplayName("Test prime number properties")
    void testPrimeNumberProperties() {
        // For any prime p, φ(p) = p-1
        long[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
        for (long p : primes) {
            assertEquals(p - 1, MathP37.totientPhi(p),
                    "For prime number " + p + ", φ(p) should be p-1");
        }
    }

    @Test
    @DisplayName("Test power of prime properties")
    void testPowerOfPrimeProperties() {
        // For p^n where p is prime, φ(p^n) = p^(n-1) * (p-1)
        assertEquals(1, MathP37.totientPhi(2));     // 2^1
        assertEquals(2, MathP37.totientPhi(4));     // 2^2
        assertEquals(4, MathP37.totientPhi(8));     // 2^3
        assertEquals(8, MathP37.totientPhi(16));    // 2^4
        assertEquals(6, MathP37.totientPhi(9));     // 3^2
        assertEquals(18, MathP37.totientPhi(27));   // 3^3
        assertEquals(20, MathP37.totientPhi(25));   // 5^2
    }

    @Test
    @DisplayName("Test multiplicative property")
    void testMultiplicativeProperty() {
        // If a and b are coprime, then φ(a*b) = φ(a) * φ(b)
        assertEquals(MathP37.totientPhi(15),
                MathP37.totientPhi(3) * MathP37.totientPhi(5),
                "φ(15) should equal φ(3) * φ(5)");

        assertEquals(MathP37.totientPhi(21),
                MathP37.totientPhi(3) * MathP37.totientPhi(7),
                "φ(21) should equal φ(3) * φ(7)");
    }

    @Test
    @DisplayName("Compare performance with primitive method")
    void testPerformanceComparison() {
        long[] testNumbers = {315L, 1024L, 12345L, 123456L};

        for (long n : testNumbers) {
            long[] times = MathP37.comparePerformance(n);
            System.out.printf("Number %d: Primitive: %dns, Improved: %dns%n",
                    n, times[0], times[1]);
            // The improved method should generally be faster for larger numbers
            assertTrue(times[1] <= times[0] * 1.15,
                    "Improved method should not be significantly slower");
        }
    }

    @Test
    @DisplayName("Test larger numbers")
    void testLargerNumbers() {
        assertEquals(512, MathP37.totientPhi(1024));    // 2^10
        assertEquals(41088, MathP37.totientPhi(123456)); // Larger composite
    }
}
