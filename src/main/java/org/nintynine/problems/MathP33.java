package org.nintynine.problems;

/**
 * P33: Determine whether two positive integer numbers are coprime. Two numbers are coprime (or
 * relatively prime) if their greatest common divisor equals 1.
 */
public class MathP33 {
  private MathP33() {}

  /**
   * Determines if two numbers are coprime. Numbers are coprime if their greatest common divisor is
   * 1.
   *
   * @param a first positive integer
   * @param b second positive integer
   * @return true if the numbers are coprime, false otherwise
   * @throws IllegalArgumentException if either number is not positive
   */
  public static boolean areCoprime(long a, long b) {
    if (a <= 0 || b <= 0) {
      throw new IllegalArgumentException("Both numbers must be positive");
    }

    return MathP32.gcd(a, b) == 1;
  }
}
