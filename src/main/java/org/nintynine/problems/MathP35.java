package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * P35: Determine the prime factors of a given positive integer.
 */
public final class MathP35 {
  private MathP35() {
    // utility class
  }

  /**
   * Determines the prime factors of a given number.
   *
   * @param n the number to factorize
   * @return list of prime factors in ascending order
   * @throws IllegalArgumentException if n is not positive
   */
  public static List<Long> primeFactors(long n) {
    List<MathP36.PrimeFactor> multis = MathP36.primeFactorsMult(n);
    List<Long> factors = new ArrayList<>();
    for (MathP36.PrimeFactor pf : multis) {
      for (int i = 0; i < pf.multiplicity(); i++) {
        factors.add(pf.factor());
      }
    }
    return factors;
  }
}
