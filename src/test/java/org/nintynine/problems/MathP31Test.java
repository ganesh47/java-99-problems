package org.nintynine.problems;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MathP31Test {

    @ParameterizedTest(name = "{0} is prime")
    @ValueSource(longs = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
            53, 59, 61, 67, 71, 73, 79, 83, 89, 97})
    void testPrimeNumbers(long number) {
        assertTrue(MathP31.isPrime(number));
    }

    @ParameterizedTest(name = "{0} is not prime")
    @ValueSource(longs = {1, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24,
            25, 26, 27, 28, 30, 32, 33, 34, 35, 36})
    void testNonPrimeNumbers(long number) {
        assertFalse(MathP31.isPrime(number));
    }

    @Test
    void testLargePrimeNumber() {
        assertTrue(MathP31.isPrime(7919)); // 1000th prime number
    }

    @Test
    void testLargeNonPrimeNumber() {
        assertFalse(MathP31.isPrime(7917)); // 7917 = 3 × 2639
    }

    @Test
    void testNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> MathP31.isPrime(-7));
    }

    @Test
    void testZero() {
        assertThrows(IllegalArgumentException.class, () -> MathP31.isPrime(0));
    }
}
