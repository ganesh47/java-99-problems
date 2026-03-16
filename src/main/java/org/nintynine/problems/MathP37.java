package org.nintynine.problems;

import java.util.List;

/**
 * P37: Calculate Euler's totient function phi(m) (improved version).
 */
public final class MathP37 {
  private MathP37() {
    // utility class
  }

  /**
   * Calculates Euler's totient function phi(m) using prime factorization.
   *
   * @param m the number to calculate phi for
   * @return the value of phi(m)
   * @throws IllegalArgumentException if m is not positive
   */
  public static long totientPhi(long m) {
    if (m <= 0) {
      throw new IllegalArgumentException("Number must be positive");
    }
    if (m == 1) {
      return 1;
    }
    List<MathP36.PrimeFactor> factors = MathP36.primeFactorsMult(m);
    long result = 1;
    for (MathP36.PrimeFactor pf : factors) {
      result *= (pf.factor() - 1) * Math.pow(pf.factor(), pf.multiplicity() - 1);
    }
    return result;
  }

  /**
   * Compares performance between the primitive (P34) and improved (P37) methods.
   *
   * @param m the number to test
   * @return array containing [primitive_time, improved_time] in nanoseconds
   */
  public static long[] comparePerformance(long m) {
    List<MathP38.PerformanceMetrics> metrics = MathP38.compare(m);
    return new long[] { metrics.get(0).timeNanos(), metrics.get(1).timeNanos() };
  }
}
