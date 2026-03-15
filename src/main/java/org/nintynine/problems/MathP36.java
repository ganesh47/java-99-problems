package org.nintynine.problems;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * P36: Determine the prime factors of a given positive integer with their multiplicities.
 */
public final class MathP36 {

  private MathP36() {
    // utility class
  }

  /** Represents a prime factor and its multiplicity. */
  public record PrimeFactor(long factor, int multiplicity) {
    @Override
    public String toString() {
      return "(" + factor + " " + multiplicity + ")";
    }
  }

  /** Interface for counting operations during prime factorization. */
  public interface OperationCounter {
    void countArithmetic();
    void countComparison();
    void countMethodCall();
  }

  /** Default no-op counter. */
  private static final OperationCounter NO_OP = new OperationCounter() {
    @Override public void countArithmetic() {}
    @Override public void countComparison() {}
    @Override public void countMethodCall() {}
  };

  /**
   * Determines the prime factors of a given number and their multiplicities.
   *
   * @param n the number to factorize
   * @return list of prime factors with their multiplicities
   */
  public static List<PrimeFactor> primeFactorsMult(long n) {
    return getPrimeFactors(n, NO_OP);
  }

  /**
   * Instrumented version of prime factorization.
   *
   * @param n number to factorize
   * @param counter operation counter
   * @return list of prime factors
   */
  public static List<PrimeFactor> getPrimeFactors(long n, OperationCounter counter) {
    if (n <= 0) {
      throw new IllegalArgumentException("Number must be positive");
    }
    counter.countMethodCall();
    List<PrimeFactor> factorsList = new ArrayList<>();

    if (n == 1) {
      counter.countComparison();
      return factorsList;
    }

    // Handle factor 2
    int count = 0;
    while (n % 2 == 0) {
      counter.countComparison();
      counter.countArithmetic();
      count++;
      counter.countArithmetic();
      n /= 2;
      counter.countArithmetic();
    }
    if (count > 0) {
      counter.countComparison();
      factorsList.add(new PrimeFactor(2, count));
    }

    // Handle odd factors
    for (long i = 3; i * i <= n; i += 2) {
      counter.countArithmetic();
      counter.countComparison();
      counter.countArithmetic();

      count = 0;
      while (n % i == 0) {
        counter.countArithmetic();
        counter.countComparison();
        count++;
        counter.countArithmetic();
        n /= i;
        counter.countArithmetic();
      }
      if (count > 0) {
        counter.countComparison();
        factorsList.add(new PrimeFactor(i, count));
      }
    }

    if (n > 2) {
      counter.countComparison();
      factorsList.add(new PrimeFactor(n, 1));
    }

    return factorsList;
  }

  /** Reconstructs the original number from its prime factorization. */
  public static long reconstruct(List<PrimeFactor> factors) {
    return factors.stream()
        .mapToLong(f -> powLong(f.factor, f.multiplicity))
        .reduce(1L, Math::multiplyExact);
  }

  private static long powLong(long base, int exponent) {
    return BigInteger.valueOf(base).pow(exponent).longValueExact();
  }
}
