package org.nintynine.problems;

import java.util.BitSet;
import java.util.Optional;

/**
 * P40: Goldbach's conjecture implementation.
 */
public final class MathP40 {
  private MathP40() {}

  /**
   * Finds a pair of prime numbers that sum up to the given even number.
   *
   * @param n the even number to decompose (must be > 2)
   * @return Optional containing the Goldbach pair if found
   * @throws IllegalArgumentException if n ≤ 2 or n is odd
   */
  public static Optional<GoldbachPair> goldbach(long n) {
    validateEven(n);
    BitSet sieve = MathP31.sieve(n);

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
   * @param n the even number to decompose
   * @return array of GoldbachPairs
   */
  public static GoldbachPair[] goldbachAll(long n) {
    validateEven(n);
    BitSet sieve = MathP31.sieve(n);
    java.util.List<GoldbachPair> pairs = new java.util.ArrayList<>();
    for (long p = 2; p <= n / 2; p++) {
      if (sieve.get((int) p)) {
        long q = n - p;
        if (q < n && sieve.get((int) q)) {
          pairs.add(new GoldbachPair(p, q));
        }
      }
    }
    return pairs.toArray(new GoldbachPair[0]);
  }

  /**
   * Verifies Goldbach's conjecture for all even numbers up to n.
   *
   * @param n upper limit
   * @return true if conjecture holds
   */
  public static boolean verifyGoldbachUpTo(long n) {
    if (n <= 2) {
      throw new IllegalArgumentException("Number must be greater than 2");
    }
    BitSet sieve = MathP31.sieve(n);
    for (long i = 4; i <= n; i += 2) {
      boolean found = false;
      for (long p = 2; p <= i / 2; p++) {
        if (sieve.get((int) p) && sieve.get((int) (i - p))) {
          found = true;
          break;
        }
      }
      if (!found) {
        return false;
      }
    }
    return true;
  }

  private static void validateEven(long n) {
    if (n <= 2) {
      throw new IllegalArgumentException("Number must be greater than 2");
    }
    if (n % 2 != 0) {
      throw new IllegalArgumentException("Number must be even");
    }
  }

  /** Represents a pair of prime numbers that sum to a given even number. */
  public record GoldbachPair(long prime1, long prime2) {
    /** Verifies that this is a valid Goldbach pair. */
    public boolean isValid() {
      return MathP31.isPrime(prime1) && MathP31.isPrime(prime2) && (prime1 + prime2) % 2 == 0;
    }

    @Override
    public String toString() {
      return String.format("(%d %d)", prime1, prime2);
    }
  }
}
