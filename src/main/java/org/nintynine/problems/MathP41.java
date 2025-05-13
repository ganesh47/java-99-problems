package org.nintynine.problems;

import java.util.*;
import java.util.stream.Collectors;

/**
 * P41: List of Goldbach compositions.
 * Lists all even numbers and their Goldbach compositions in a given range.
 */
public class MathP41 {
    /**
     * Represents a Goldbach composition list entry
     */
    public record GoldbachListEntry(long number, MathP40.GoldbachPair pair) {
        @Override
        public String toString() {
            return String.format("%d = %d + %d",
                    number, pair.prime1(), pair.prime2());
        }
    }

    /**
     * Lists all Goldbach compositions in the given range.
     *
     * @param start lower bound of the range (inclusive)
     * @param end upper bound of the range (inclusive)
     * @return list of Goldbach compositions
     * @throws IllegalArgumentException if start > end or start < 0
     */
    public static List<GoldbachListEntry> goldbachList(long start, long end) {
        return goldbachList(start, end, 0);
    }

    /**
     * Lists Goldbach compositions in the given range where both primes are
     * greater than or equal to the specified minimum value.
     *
     * @param start lower bound of the range (inclusive)
     * @param end upper bound of the range (inclusive)
     * @param minPrime minimum value for both primes (0 for no minimum)
     * @return filtered list of Goldbach compositions
     * @throws IllegalArgumentException if start > end or start < 0 or minPrime < 0
     */
    public static List<GoldbachListEntry> goldbachList(long start, long end,
                                                       long minPrime) {
        validateRange(start, end);
        if (minPrime < 0) {
            throw new IllegalArgumentException("Minimum prime must be non-negative");
        }

        List<GoldbachListEntry> compositions = new ArrayList<>();

        // Adjust start to first even number in range
        long firstEven = (start % 2 == 0) ? start : start + 1;

        // Use sieve for the entire range for efficiency
        BitSet sieve = sieveOfEratosthenes(end);

        // Find compositions for each even number
        for (long n = firstEven; n <= end; n += 2) {
            if (n > 2) { // Skip 2 as it's not a sum of two primes
                Optional<MathP40.GoldbachPair> composition =
                        findGoldbachComposition(n, minPrime, sieve);
                long finalN = n;
                composition.ifPresent(pair ->
                        compositions.add(new GoldbachListEntry(finalN, pair)));
            }
        }

        return compositions;
    }

    /**
     * Counts Goldbach compositions where both primes are above the minimum value.
     *
     * @param start lower bound of the range (inclusive)
     * @param end upper bound of the range (inclusive)
     * @param minPrime minimum value for both primes
     * @return count of qualifying Goldbach compositions
     */
    public static long countGoldbachCompositions(long start, long end,
                                                 long minPrime) {
        return goldbachList(start, end, minPrime).size();
    }

    /**
     * Finds Goldbach composition for a number with prime minimum value constraint.
     */
    private static Optional<MathP40.GoldbachPair> findGoldbachComposition(
            long n, long minPrime, BitSet sieve) {
        // Start from minPrime if specified, otherwise from 2
        for (long p = Math.max(2, minPrime); p <= n/2; p++) {
            if (sieve.get((int)p)) {
                long q = n - p;
                if (q >= minPrime && q < n && sieve.get((int)q)) {
                    return Optional.of(new MathP40.GoldbachPair(p, q));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Generates an optimized Sieve of Eratosthenes up to n.
     */
    private static BitSet sieveOfEratosthenes(long n) {
        BitSet sieve = new BitSet((int)n + 1);
        sieve.set(2, (int)n + 1); // Set all bits initially

        for (int i = 2; (long) i * i <= n; i++) {
            if (sieve.get(i)) {
                for (int j = i * i; j <= n; j += i) {
                    sieve.clear(j);
                }
            }
        }

        return sieve;
    }

    /**
     * Validates the range parameters.
     */
    private static void validateRange(long start, long end) {
        if (start > end) {
            throw new IllegalArgumentException("Start must not be greater than end");
        }
        if (start < 0) {
            throw new IllegalArgumentException("Range must be non-negative");
        }
    }

    /**
     * Returns the compositions as a formatted string.
     */
    public static String formatGoldbachList(List<GoldbachListEntry> compositions) {
        return compositions.stream()
                .map(GoldbachListEntry::toString)
                .collect(Collectors.joining("\n"));
    }
}
