package org.nintynine.problems;

import java.util.BitSet;
import java.util.Optional;

/**
 * P40: Goldbach's conjecture implementation.
 * Finds two prime numbers that sum up to a given even number.
 */
public class MathP40 {
    /**
     * Finds a pair of prime numbers that sum up to the given even number.
     * Returns the first such pair found (typically with the smallest first prime).
     *
     * @param n the even number to decompose (must be > 2)
     * @return Optional containing the Goldbach pair if found
     * @throws IllegalArgumentException if n ≤ 2 or n is odd
     */
    public static Optional<GoldbachPair> goldbach(long n) {
        if (n <= 2) {
            throw new IllegalArgumentException("Number must be greater than 2");
        }
        if (n % 2 != 0) {
            throw new IllegalArgumentException("Number must be even");
        }

        BitSet sieve = sieveOfEratosthenes(n);

        for (long p = 2; p <= n / 2; p++) {
            if (sieve.get((int) p)) {
                long q = n - p;
                if (q < n && sieve.get((int) q)) {
                    return Optional.of(new GoldbachPair(p, q));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Finds all pairs of prime numbers that sum up to the given even number.
     *
     * @param n the even number to decompose (must be > 2)
     * @return array of GoldbachPairs, ordered by first prime
     * @throws IllegalArgumentException if n ≤ 2 or n is odd
     */
    public static GoldbachPair[] goldbachAll(long n) {
        if (n <= 2) {
            throw new IllegalArgumentException("Number must be greater than 2");
        }
        if (n % 2 != 0) {
            throw new IllegalArgumentException("Number must be even");
        }

        BitSet sieve = sieveOfEratosthenes(n);
        int pairCount = 0;
        for (long p = 2; p <= n / 2; p++) {
            if (sieve.get((int) p) && sieve.get((int) (n - p))) {
                pairCount++;
            }
        }

        GoldbachPair[] pairs = new GoldbachPair[pairCount];
        int index = 0;
        for (long p = 2; p <= n / 2; p++) {
            if (sieve.get((int) p)) {
                long q = n - p;
                if (q < n && sieve.get((int) q)) {
                    pairs[index++] = new GoldbachPair(p, q);
                }
            }
        }

        return pairs;
    }

    /**
     * Verifies Goldbach's conjecture for all even numbers up to n.
     *
     * @param n upper limit to check (must be > 2)
     * @return true if conjecture holds for all even numbers in range
     * @throws IllegalArgumentException if n ≤ 2
     */
    public static boolean verifyGoldbachUpTo(long n) {
        if (n <= 2) {
            throw new IllegalArgumentException("Number must be greater than 2");
        }

        BitSet sieve = sieveOfEratosthenes(n);

        for (long i = 4; i <= n; i += 2) {
            boolean found = false;

            for (long p = 2; p <= i / 2; p++) {
                if (sieve.get((int) p)) {
                    long q = i - p;
                    if (q < i && sieve.get((int) q)) {
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }

    /**
     * Generates prime number sieve up to n.
     * Uses BitSet for efficient storage and lookup.
     */
    private static BitSet sieveOfEratosthenes(long n) {
        BitSet sieve = new BitSet((int) n + 1);
        sieve.set(2, (int) n + 1);

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
     * Represents a pair of prime numbers that sum to a given even number.
     */
    public record GoldbachPair(long prime1, long prime2) {
        /**
         * Verifies that this is a valid Goldbach pair.
         *
         * @return true if both numbers are prime and sum to an even number
         */
        public boolean isValid() {
            return MathP31.isPrime(prime1) &&
                    MathP31.isPrime(prime2) &&
                    (prime1 + prime2) % 2 == 0;
        }

        @Override
        public String toString() {
            return String.format("(%d %d)", prime1, prime2);
        }
    }
}
