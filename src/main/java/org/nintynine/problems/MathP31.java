package org.nintynine.problems;

/**
 * P31: Determine whether a given integer number is prime.
 */
public class MathP31 {
    private MathP31() {
    }

    /**
     * Determines if a number is prime.
     * A prime number is a natural number greater than 1 that is only divisible by 1 and itself.
     *
     * @param n the number to check
     * @return true if the number is prime, false otherwise
     * @throws IllegalArgumentException if n is less than or equal to 0
     */
    public static boolean isPrime(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number must be positive");
        }
        if (n == 1) {
            return false;  // 1 is not prime by definition
        }
        if (n == 2 || n == 3) {
            return true;  // 2 and 3 are prime
        }
        if (n % 2 == 0) {
            return false;  // Even numbers greater than 2 are not prime
        }

        // We only need to check up to the square root of n
        // Using only odd numbers since we already excluded even numbers
        long sqrt = (long) Math.sqrt(n);
        for (long i = 3; i <= sqrt; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
