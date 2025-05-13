package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * P35: Determine the prime factors of a given positive integer.
 * Returns a flat list containing the prime factors in ascending order.
 */
public class MathP35 {
    /**
     * Determines the prime factors of a given number.
     * Returns a list of prime factors in ascending order, including duplicates
     * for multiple occurrences of the same prime factor.
     * Example:
     * primeFactors(315) returns [3, 3, 5, 7]
     *
     * @param n the number to factorize
     * @return list of prime factors in ascending order
     * @throws IllegalArgumentException if n is not positive
     */
    public static List<Long> primeFactors(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number must be positive");
        }

        List<Long> factors = new ArrayList<>();

        if (n == 1) {
            return factors; // Empty list for 1
        }

        // Handle all factors of 2 first
        while (n % 2 == 0) {
            factors.add(2L);
            n /= 2;
        }

        // Handle odd factors
        for (long i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }

        // If n is greater than 2 here, it is prime
        if (n > 2) {
            factors.add(n);
        }

        return factors;
    }
}
