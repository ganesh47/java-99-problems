package org.nintynine.problems;

import java.util.stream.LongStream;

/**
 * P34: Calculate Euler's totient function phi(m). Euler's totient function phi(m) is defined as the
 * number of positive integers r (1 <= r < m) that are coprime to m.
 */
public class MathP34 {
  /**
   * Calculates Euler's totient function phi(m) using the primitive method. For a number m, it
   * counts how many numbers from 1 to m-1 are coprime with m. Special properties: 1. For prime
   * numbers p, phi(p) = p-1 2. For n = 1, phi(1) = 1
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

    // For optimization, first check if m is prime
    if (MathP31.isPrime(m)) {
      return m - 1; // For prime p, phi(p) = p-1
    }

    // Primitive method: count coprime numbers from 1 to m-1
    return LongStream.range(1, m).filter(r -> MathP33.areCoprime(r, m)).count();
  }

  /**
   * Determines if a number is prime and returns its totient value. This is a helper method to
   * demonstrate the relationship between prime numbers and their totient values.
   *
   * @param m the number to check and calculate totient for
   * @return a record containing whether the number is prime and its totient value
   * @throws IllegalArgumentException if m is not positive
   */
  public static PrimeTotientResult checkPrimeAndTotient(long m) {
    boolean isPrime = MathP31.isPrime(m);
    long totient = totientPhi(m);
    return new PrimeTotientResult(isPrime, totient);
  }

  /** Record to hold the result of prime check and totient calculation */
  public record PrimeTotientResult(boolean isPrime, long totientValue) {
    @Override
    public String toString() {
      return "Number is " + (isPrime ? "prime" : "not prime") + ", φ(m) = " + totientValue;
    }
  }
}
