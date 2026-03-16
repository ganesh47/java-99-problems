package org.nintynine.problems;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * P41: List of Goldbach compositions.
 */
public final class MathP41 {
  private MathP41() {}

  /**
   * Lists all Goldbach compositions in the given range.
   *
   * @param start lower bound
   * @param end upper bound
   * @return list of Goldbach compositions
   */
  public static List<GoldbachListEntry> goldbachList(long start, long end) {
    return goldbachList(start, end, 0);
  }

  /**
   * Lists Goldbach compositions in the given range where both primes are above minimum.
   *
   * @param start lower bound
   * @param end upper bound
   * @param minPrime minimum prime value
   * @return filtered list of Goldbach compositions
   */
  public static List<GoldbachListEntry> goldbachList(long start, long end, long minPrime) {
    validateRange(start, end);
    if (minPrime < 0) {
      throw new IllegalArgumentException("Minimum prime must be non-negative");
    }
    List<GoldbachListEntry> compositions = new ArrayList<>();
    long firstEven = (start % 2 == 0) ? start : start + 1;
    BitSet sieve = MathP31.sieve(end);

    for (long n = firstEven; n <= end; n += 2) {
      if (n > 2) {
        final long currentN = n;
        Optional<MathP40.GoldbachPair> composition = findGoldbachComposition(currentN, minPrime, sieve);
        composition.ifPresent(pair -> compositions.add(new GoldbachListEntry(currentN, pair)));
      }
    }
    return compositions;
  }

  public static long countGoldbachCompositions(long start, long end, long minPrime) {
    return goldbachList(start, end, minPrime).size();
  }

  private static Optional<MathP40.GoldbachPair> findGoldbachComposition(
      long n, long minPrime, BitSet sieve) {
    for (long p = Math.max(2, minPrime); p <= n / 2; p++) {
      if (sieve.get((int) p)) {
        long q = n - p;
        if (q >= minPrime && q < n && sieve.get((int) q)) {
          return Optional.of(new MathP40.GoldbachPair(p, q));
        }
      }
    }
    return Optional.empty();
  }

  private static void validateRange(long start, long end) {
    if (start > end) {
      throw new IllegalArgumentException("Start must not be greater than end");
    }
    if (start < 0) {
      throw new IllegalArgumentException("Range must be non-negative");
    }
  }

  public static String formatGoldbachList(List<GoldbachListEntry> compositions) {
    return compositions.stream().map(GoldbachListEntry::toString).collect(Collectors.joining("\n"));
  }

  /** Represents a Goldbach composition list entry. */
  public record GoldbachListEntry(long number, MathP40.GoldbachPair pair) {
    @Override
    public String toString() {
      return String.format("%d = %d + %d", number, pair.prime1(), pair.prime2());
    }
  }
}
