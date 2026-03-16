package org.nintynine.problems;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/** P39: Generate a list of prime numbers in a given range. */
public final class MathP39 {
  private MathP39() {
    // utility class
  }

  /**
   * Returns a list of all prime numbers in the range [start, end] inclusive.
   *
   * @param start the lower bound of the range (inclusive)
   * @param end the upper bound of the range (inclusive)
   * @return list of prime numbers in the range
   * @throws IllegalArgumentException if start > end or start < 0
   */
  public static List<Long> primesInRange(long start, long end) {
    if (start > end) {
      throw new IllegalArgumentException("Start must not be greater than end");
    }
    if (start < 0) {
      throw new IllegalArgumentException("Range must be non-negative");
    }

    List<Long> primes = new ArrayList<>();
    if (end < 2) {
      return primes;
    }

    start = Math.max(2, start);

    if (end - start < 1000) {
      for (long num = start; num <= end; num++) {
        if (MathP31.isPrime(num)) {
          primes.add(num);
        }
      }
      return primes;
    }

    return primesInRangeSegmentedSieve(start, end);
  }

  /**
   * Counts the number of primes in the given range [start, end].
   *
   * @param start lower bound
   * @param end upper bound
   * @return prime count
   */
  public static long countPrimesInRange(long start, long end) {
    return primesInRange(start, end).size();
  }

  private static List<Long> primesInRangeSegmentedSieve(long start, long end) {
    int segmentSize = 32768;
    List<Long> primes = new ArrayList<>();

    int sqrt = (int) Math.sqrt(end);
    List<Integer> smallPrimesList = new ArrayList<>();
    BitSet smallSieve = MathP31.sieve(sqrt);
    for (int i = 2; i <= sqrt; i++) {
      if (smallSieve.get(i)) {
        smallPrimesList.add(i);
        if (i >= start) {
          primes.add((long) i);
        }
      }
    }

    long low = Math.max(start, (long) sqrt + 1);
    while (low <= end) {
      long high = Math.min(low + segmentSize - 1, end);
      BitSet segment = new BitSet((int) (high - low + 1));
      segment.set(0, (int) (high - low + 1));

      for (int p : smallPrimesList) {
        long startIdx = (low + p - 1) / p * p;
        for (long j = Math.max(startIdx, (long) p * p); j <= high; j += p) {
          segment.clear((int) (j - low));
        }
      }

      for (long i = low; i <= high; i++) {
        if (segment.get((int) (i - low))) {
          primes.add(i);
        }
      }
      low += segmentSize;
    }

    return primes;
  }
}
