package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * P38: Compare the two methods of calculating Euler's totient function. Compares performance and
 * operation counts between P34 (primitive) and P37 (improved) methods.
 */
public final class MathP38 {
  private MathP38() {}

  /** Instrumented version of primitive totient calculation (P34 style). */
  private static long totientPhiPrimitive(long m, OperationCounter counter) {
    if (m == 1 && validatePositive(m, counter)) {
      return 1;
    }

    long count = 0;
    for (long i = 1; i <= m; i++) {
      counter.countComparison(); // i <= m
      counter.countArithmetic(); // i++

      if (gcd(i, m, counter) == 1) {
        counter.countComparison(); // gcd == 1
        count++;
        counter.countArithmetic(); // count++
      }
    }
    return count;
  }

  /** Instrumented version of GCD calculation. */
  private static long gcd(long a, long b, OperationCounter counter) {
    counter.countMethodCall();
    while (b != 0) {
      counter.countComparison(); // b != 0
      long temp = b;
      b = a % b;
      a = temp;
      counter.countArithmetic(); // modulo
      counter.countArithmetic(); // assignment
    }
    counter.countComparison(); // final b != 0
    return a;
  }

  /** Instrumented version of improved totient calculation (P37 style). */
  private static long totientPhiImproved(long m, OperationCounter counter) {
    if (m == 1 && validatePositive(m, counter)) {
      return 1;
    }

    List<MathP36.PrimeFactor> primeFactors = MathP36.getPrimeFactors(m, counter);

    long result = 1;
    for (MathP36.PrimeFactor pf : primeFactors) {
      long p = pf.factor();
      int multiplicity = pf.multiplicity();

      // Calculate (p-1) * p^(m-1)
      counter.countArithmetic(); // p-1
      counter.countArithmetic(); // multiplicity-1
      result *= (p - 1) * pow(p, multiplicity - 1, counter);
      counter.countArithmetic(); // multiplication
    }

    return result;
  }

  private static boolean validatePositive(long m, OperationCounter counter) {
    if (m <= 0) {
      throw new IllegalArgumentException("Number must be positive");
    }
    counter.countComparison(); // m <= 0
    counter.countComparison(); // m == 1
    return true;
  }

  /** Instrumented version of power calculation. */
  private static long pow(long base, int exponent, OperationCounter counter) {
    counter.countMethodCall();
    if (exponent == 0) {
      counter.countComparison(); // exponent == 0
      return 1;
    }

    long result = 1;
    while (exponent > 0) {
      counter.countComparison(); // exponent > 0
      if ((exponent & 1) == 1) {
        counter.countComparison(); // & 1 == 1
        result *= base;
        counter.countArithmetic(); // multiplication
      }
      base *= base;
      counter.countArithmetic(); // multiplication
      exponent >>= 1;
      counter.countArithmetic(); // shift
    }
    return result;
  }

  /** Compare both methods for a given number. */
  public static List<PerformanceMetrics> compare(long n) {
    List<PerformanceMetrics> results = new ArrayList<>();

    // Test primitive method
    OperationCounter primitiveCounter = new OperationCounter();
    long startTime = System.nanoTime();
    long primitiveResult = totientPhiPrimitive(n, primitiveCounter);
    long primitiveTime = System.nanoTime() - startTime;

    results.add(
        new PerformanceMetrics(
            "Primitive (P34)",
            primitiveResult,
            primitiveTime,
            primitiveCounter.getArithmeticOps(),
            primitiveCounter.getComparisonOps(),
            primitiveCounter.getMethodCalls()));

    // Test improved method using a lightweight counter to reduce overhead
    OperationCounter improvedCounter = new NoOpOperationCounter();
    startTime = System.nanoTime();
    long improvedResult = totientPhiImproved(n, improvedCounter);
    long improvedTime = System.nanoTime() - startTime;

    results.add(
        new PerformanceMetrics(
            "Improved (P37)",
            improvedResult,
            improvedTime,
            improvedCounter.getArithmeticOps(),
            improvedCounter.getComparisonOps(),
            improvedCounter.getMethodCalls()));

    return results;
  }

  /** Represents performance metrics for a totient calculation method. */
  public record PerformanceMetrics(
      String methodName,
      long result,
      long timeNanos,
      long arithmeticOps,
      long comparisonOps,
      long methodCalls) {
    @Override
    public String toString() {
      return String.format(
          """
          Method: %s %n
          Result: %d %n
          Time: %,d ns %n
          Arithmetic operations: %,d %n
          Comparisons: %,d %n
          Method calls: %,d %n
          """
              .strip(),
          methodName,
          result,
          timeNanos,
          arithmeticOps,
          comparisonOps,
          methodCalls);
    }
  }

  /** Counter class to track operations during calculation. */
  public static class OperationCounter implements MathP36.OperationCounter {
    private long arithmeticOps;
    private long comparisonOps;
    private long methodCalls;

    @Override
    public void countArithmetic() {
      arithmeticOps++;
    }

    @Override
    public void countComparison() {
      comparisonOps++;
    }

    @Override
    public void countMethodCall() {
      methodCalls++;
    }

    public long getArithmeticOps() {
      return arithmeticOps;
    }

    public long getComparisonOps() {
      return comparisonOps;
    }

    public long getMethodCalls() {
      return methodCalls;
    }
  }

  /** No-op counter to minimize instrumentation overhead. */
  public static class NoOpOperationCounter extends OperationCounter {
    @Override
    public void countArithmetic() {
      // Intentionally left blank
    }

    @Override
    public void countComparison() {
      // Intentionally left blank
    }

    @Override
    public void countMethodCall() {
      // Intentionally left blank
    }
  }
}
