package org.nintynine.problems;

import java.util.List;

/**
 * P37: Calculate Euler's totient function phi(m) (improved version). Uses prime factorization to
 * calculate phi(m) more efficiently. For a number m with prime factorization ((p1 m1) (p2 m2) (p3
 * m3) ...), phi(m) = (p1 - 1) * p1^(m1 - 1) * (p2 - 1) * p2^(m2 - 1) * ...
 */
public class MathP37 {
  private MathP37() {}

  /**
   * Calculates Euler's totient function phi(m) using prime factorization. This is more efficient
   * than the primitive method from P34.
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
      return 1; // Special case: phi(1) = 1
    }

    // Get prime factorization
    List<MathP36.PrimeFactor> primeFactors = MathP36.primeFactorsMult(m);

    // Apply the formula:
    // phi(m) = (p1 - 1) * p1^(m1 - 1) * (p2 - 1) * p2^(m2 - 1) * ...
    long result = 1;
    for (MathP36.PrimeFactor pf : primeFactors) {
      long p = pf.factor();
      int multiplicity = pf.multiplicity();

      // Calculate (p-1) * p^(m-1)
      result *= (p - 1) * pow(p, multiplicity - 1);
    }

    return result;
  }

  /**
   * Helper method to calculate power with positive integer exponent.
   *
   * @param base the base number
   * @param exponent the exponent (must be non-negative)
   * @return base raised to the power of exponent
   */
  private static long pow(long base, int exponent) {
    if (exponent < 0) {
      throw new IllegalArgumentException("Exponent must be non-negative");
    }

    if (exponent == 0) {
      return 1;
    }

    long result = 1;
    while (exponent > 0) {
      if ((exponent & 1) == 1) {
        result *= base;
      }
      base *= base;
      exponent >>= 1;
    }
    return result;
  }

  /**
   * Compares performance between the primitive (P34) and improved (P37) methods. Returns the
   * execution times for both methods.
   *
   * @param m the number to test
   * @return array containing [primitive_time, improved_time] in nanoseconds
   */
  public static long[] comparePerformance(long m) {
    long startTime;
    long endTime;
    long[] times = new long[2];

    // Test primitive method (P34)
    startTime = System.nanoTime();
    long result1 = MathP34.totientPhi(m);
    endTime = System.nanoTime();
    times[0] = endTime - startTime;

    // Test improved method (P37)
    startTime = System.nanoTime();
    long result2 = totientPhi(m);
    endTime = System.nanoTime();
    times[1] = endTime - startTime;

    // Verify results match
    if (result1 != result2) {
      throw new IllegalStateException("Inconsistent results: " + result1 + " != " + result2);
    }

    return times;
  }
}
