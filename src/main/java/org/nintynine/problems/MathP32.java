package org.nintynine.problems;

/**
 * P32: Determine the greatest common divisor of two positive integer numbers using Euclid's algorithm.
 */
public class MathP32 {
    private MathP32() {}

    /**
     * Calculates the Greatest Common Divisor (GCD) of two numbers using Euclid's algorithm.
     * Euclid's algorithm states that the greatest common divisor of two numbers a and b
     * is the same as the greatest common divisor of b and the remainder of a divided by b.
     *
     * @param a first positive integer
     * @param b second positive integer
     * @return the greatest common divisor of a and b
     * @throws IllegalArgumentException if either number is not positive
     */
    public static long gcd(long a, long b) {
        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("Both numbers must be positive");
        }

        // Ensure a is the larger number
        if (a < b) {
            long temp = a;
            a = b;
            b = temp;
        }

        // Euclid's algorithm: gcd(a,b) = gcd(b,a mod b)
        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }

        return a;
    }

    /**
     * Recursive implementation of Euclid's algorithm.
     * Provided as an alternative approach.
     *
     * @param a first positive integer
     * @param b second positive integer
     * @return the greatest common divisor of a and b
     * @throws IllegalArgumentException if either number is not positive
     */
    public static long gcdRecursive(long a, long b) {
        if (a <= 0 || b < 0) {
            throw new IllegalArgumentException("Both numbers must be positive");
        }

        if (b == 0) {
            return a;
        }

        return gcdRecursive(b, a % b);
    }
}
