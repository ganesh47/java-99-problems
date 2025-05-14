package org.nintynine.problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MathP39Test {

    @Test
    @DisplayName("Test small ranges")
    void testSmallRanges() {
        // Test [1,10]
        List<Long> primes = MathP39.primesInRange(1, 10);
        assertEquals(List.of(2L, 3L, 5L, 7L), primes);

        // Test [10,20]
        primes = MathP39.primesInRange(10, 20);
        assertEquals(List.of(11L, 13L, 17L, 19L), primes);

        // Test [95,100]
        primes = MathP39.primesInRange(95, 100);
        assertEquals(List.of(97L), primes);
    }

    @Test
    @DisplayName("Test range starting with prime")
    void testRangeStartingWithPrime() {
        List<Long> primes = MathP39.primesInRange(17, 30);
        assertEquals(List.of(17L, 19L, 23L, 29L), primes);
    }

    @Test
    @DisplayName("Test range ending with prime")
    void testRangeEndingWithPrime() {
        List<Long> primes = MathP39.primesInRange(20, 23);
        assertEquals(List.of(23L), primes);
    }

    @Test
    @DisplayName("Test invalid ranges")
    void testInvalidRanges() {
        assertThrows(IllegalArgumentException.class,
                () -> MathP39.primesInRange(10, 5));
        assertThrows(IllegalArgumentException.class,
                () -> MathP39.primesInRange(-5, 10));
    }

    @Test
    @DisplayName("Test edge cases")
    void testEdgeCases() {
        // Empty range
        assertTrue(MathP39.primesInRange(0, 1).isEmpty());

        // Single number range
        assertEquals(List.of(2L), MathP39.primesInRange(2, 2));

        // Range with no primes
        assertTrue(MathP39.primesInRange(24, 28).isEmpty());
    }

    @ParameterizedTest(name = "Range [{0}, {1}] should contain {2} primes")
    @CsvSource({
            "1, 10, 4",
            "1, 100, 25",
            "100, 200, 21",
            "1000, 1100, 16",
            "9900, 10000, 9",
            "9900, 100000, 8372"
    })
    void testPrimeCount(long start, long end, long expectedCount) {
        assertEquals(expectedCount, MathP39.countPrimesInRange(start, end));
        assertEquals(expectedCount, MathP39.primesInRange(start, end).size());
    }

    @Test
    @DisplayName("Test larger ranges")
    void testLargerRanges() {
        // Test performance and correctness for larger ranges
        long start = System.nanoTime();
        List<Long> primes = MathP39.primesInRange(10000, 101000000);
        long end = System.nanoTime();

        // Verify some known primes in this range
        assertTrue(primes.contains(10007L));
        assertTrue(primes.contains(10009L));
        assertTrue(primes.contains(10037L));

        // Print performance info
        System.out.printf("Generated primes in range [10000,10100] in %.3f ms%n",
                (end - start) / 1_000_000.0);
    }

    @Test
    @DisplayName("Test prime number properties")
    void testPrimeProperties() {
        List<Long> primes = MathP39.primesInRange(2, 100);

        // All numbers should be prime
        for (long prime : primes) {
            assertTrue(MathP31.isPrime(prime),
                    prime + " should be prime");
        }

        // Should include known twin primes
        List<Long> twinPrimes = List.of(3L, 5L, 7L, 11L, 13L, 17L, 19L, 29L, 31L);
        assertTrue(primes.containsAll(twinPrimes));

        // Should not include known composites
        List<Long> composites = List.of(4L, 6L, 8L, 9L, 10L, 12L, 14L, 15L, 16L);
        composites.forEach(n -> assertFalse(primes.contains(n),
                n + " should not be in the list"));
    }

    @Test
    @DisplayName("Test segmented sieve consistency")
    void testSegmentedSieveConsistency() {
        // Test different segment boundaries
        long[][] ranges = {
                {1000, 1100},
                {9900, 10100},
                {99900, 100100}
        };

        for (long[] range : ranges) {
            List<Long> primes = MathP39.primesInRange(range[0], range[1]);

            // Verify all numbers are prime
            for (long prime : primes) {
                assertTrue(MathP31.isPrime(prime),
                        prime + " should be prime");
            }

            // Verify count matches direct counting
            assertEquals(primes.size(),
                    MathP39.countPrimesInRange(range[0], range[1]));
        }
    }
}
