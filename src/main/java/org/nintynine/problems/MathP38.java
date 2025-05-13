
package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * P38: Compare the two methods of calculating Euler's totient function.
 * Compares performance and operation counts between P34 (primitive) and P37 (improved) methods.
 */
public class MathP38 {
    /**
     * Represents performance metrics for a totient calculation method
     */
    public record PerformanceMetrics(
            String methodName,
            long result,
            long timeNanos,
            long arithmeticOps,
            long comparisonOps,
            long methodCalls
    ) {
        @Override
        public String toString() {
            return String.format("""
                Method: %s %n
                Result: %d %n
                Time: %,d ns %n
                Arithmetic operations: %,d %n
                Comparisons: %,d %n
                Method calls: %,d %n
                """.strip(),
                    methodName, result, timeNanos,
                    arithmeticOps, comparisonOps, methodCalls);
        }
    }

    /**
     * Counter class to track operations during calculation
     */
    public static class OperationCounter {
        private long arithmeticOps = 0;
        private long comparisonOps = 0;
        private long methodCalls = 0;

        public void countArithmetic() { arithmeticOps++; }
        public void countComparison() { comparisonOps++; }
        public void countMethodCall() { methodCalls++; }

        public long getArithmeticOps() { return arithmeticOps; }
        public long getComparisonOps() { return comparisonOps; }
        public long getMethodCalls() { return methodCalls; }
    }

    /**
     * Instrumented version of primitive totient calculation (P34 style)
     */
    private static long totientPhiPrimitive(long m, OperationCounter counter) {
        if (m <= 0) {
            throw new IllegalArgumentException("Number must be positive");
        }
        counter.countComparison(); // m <= 0

        if (m == 1) {
            counter.countComparison(); // m == 1
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

    /**
     * Instrumented version of GCD calculation
     */
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

    /**
     * Instrumented version of improved totient calculation (P37 style)
     */
    private static long totientPhiImproved(long m, OperationCounter counter) {
        if (m <= 0) {
            throw new IllegalArgumentException("Number must be positive");
        }
        counter.countComparison(); // m <= 0

        if (m == 1) {
            counter.countComparison(); // m == 1
            return 1;
        }

        List<MathP36.PrimeFactor> primeFactors = getPrimeFactors(m, counter);

        long result = 1;
        for (MathP36.PrimeFactor pf : primeFactors) {
            long p = pf.factor();
            int multiplicity = pf.multiplicity();

            // Calculate (p-1) * p^(m-1)
            counter.countArithmetic(); // p-1
            counter.countArithmetic(); // m-1
            result *= (p - 1) * pow(p, multiplicity - 1, counter);
            counter.countArithmetic(); // multiplication
        }

        return result;
    }

    /**
     * Instrumented version of prime factorization
     */
    private static List<MathP36.PrimeFactor> getPrimeFactors(long n, OperationCounter counter) {
        counter.countMethodCall();
        List<MathP36.PrimeFactor> factors = new ArrayList<>();

        // Handle factor 2
        int count = 0;
        while (n % 2 == 0) {
            counter.countComparison(); // n % 2 == 0
            counter.countArithmetic(); // n % 2
            count++;
            counter.countArithmetic(); // count++
            n /= 2;
            counter.countArithmetic(); // division
        }
        if (count > 0) {
            counter.countComparison(); // count > 0
            factors.add(new MathP36.PrimeFactor(2, count));
        }

        // Handle odd factors
        for (long i = 3; i * i <= n; i += 2) {
            counter.countArithmetic(); // i * i
            counter.countComparison(); // <= n
            counter.countArithmetic(); // i += 2

            count = 0;
            while (n % i == 0) {
                counter.countArithmetic(); // n % i
                counter.countComparison(); // == 0
                count++;
                counter.countArithmetic(); // count++
                n /= i;
                counter.countArithmetic(); // division
            }
            if (count > 0) {
                counter.countComparison(); // count > 0
                factors.add(new MathP36.PrimeFactor(i, count));
            }
        }

        if (n > 2) {
            counter.countComparison(); // n > 2
            factors.add(new MathP36.PrimeFactor(n, 1));
        }

        return factors;
    }

    /**
     * Instrumented version of power calculation
     */
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

    /**
     * Compare both methods for a given number
     */
    public static List<PerformanceMetrics> compare(long n) {
        List<PerformanceMetrics> results = new ArrayList<>();

        // Test primitive method
        OperationCounter primitiveCounter = new OperationCounter();
        long startTime = System.nanoTime();
        long primitiveResult = totientPhiPrimitive(n, primitiveCounter);
        long primitiveTime = System.nanoTime() - startTime;

        results.add(new PerformanceMetrics(
                "Primitive (P34)",
                primitiveResult,
                primitiveTime,
                primitiveCounter.getArithmeticOps(),
                primitiveCounter.getComparisonOps(),
                primitiveCounter.getMethodCalls()
        ));

        // Test improved method
        OperationCounter improvedCounter = new OperationCounter();
        startTime = System.nanoTime();
        long improvedResult = totientPhiImproved(n, improvedCounter);
        long improvedTime = System.nanoTime() - startTime;

        results.add(new PerformanceMetrics(
                "Improved (P37)",
                improvedResult,
                improvedTime,
                improvedCounter.getArithmeticOps(),
                improvedCounter.getComparisonOps(),
                improvedCounter.getMethodCalls()
        ));

        return results;
    }
}
