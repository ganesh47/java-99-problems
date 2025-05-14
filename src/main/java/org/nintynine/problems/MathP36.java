package org.nintynine.problems;

import java.util.ArrayList;
import java.util.List;

/**
 * P36: Determine the prime factors of a given positive integer with their multiplicities.
 * Returns a list containing pairs of prime factors and their multiplicity.
 */
public class MathP36 {
    /**
     * Determines the prime factors of a given number and their multiplicities.
     * Returns a list of PrimeFactor records, each containing a prime factor
     * and how many times it appears in the factorization.
     * Example:
     * primeFactorsMult(315) returns [(3,2), (5,1), (7,1)]
     * because 315 = 3² × 5 × 7
     *
     * @param n the number to factorize
     * @return list of prime factors with their multiplicities
     * @throws IllegalArgumentException if n is not positive
     */
    public static List<PrimeFactor> primeFactorsMult(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number must be positive");
        }

        List<PrimeFactor> factorsList = new ArrayList<>();

        if (n == 1) {
            return factorsList; // Empty list for 1
        }

        // Handle factor 2 separately
        int count = 0;
        while (n % 2 == 0) {
            count++;
            n /= 2;
        }
        if (count > 0) {
            factorsList.add(new PrimeFactor(2, count));
        }

        // Handle odd factors
        for (long i = 3; i * i <= n; i += 2) {
            count = 0;
            while (n % i == 0) {
                count++;
                n /= i;
            }
            if (count > 0) {
                factorsList.add(new PrimeFactor(i, count));
            }
        }

        // If n is greater than 2 here, it is prime
        if (n > 2) {
            factorsList.add(new PrimeFactor(n, 1));
        }

        return factorsList;
    }

    /**
     * Reconstructs the original number from its prime factorization.
     * Useful for verification.
     *
     * @param factors list of prime factors with their multiplicities
     * @return the original number
     */
    public static long reconstruct(List<PrimeFactor> factors) {
        return factors.stream()
                .mapToLong(f -> (long) Math.pow(f.factor, f.multiplicity))
                .reduce(1L, (a, b) -> a * b);
    }

    /**
     * Represents a prime factor and its multiplicity (how many times it appears).
     */
    public record PrimeFactor(long factor, int multiplicity) {
        @Override
        public String toString() {
            return "(" + factor + " " + multiplicity + ")";
        }
    }
}
