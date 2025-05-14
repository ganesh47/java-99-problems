package org.nintynine.problems;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * P39: Generate a list of prime numbers in a given range.
 */
public class MathP39 {
    private MathP39() {
    }

    /**
     * Returns a list of all prime numbers in the range [start, end] inclusive.
     * Uses the Sieve of Eratosthenes algorithm optimized for ranges.
     *
     * @param start the lower bound of the range (inclusive)
     * @param end   the upper bound of the range (inclusive)
     * @return list of prime numbers in the range
     * @throws IllegalArgumentException if start > end or start < 0
     */
    public static List<Long> primesInRange(long start, long end) {
        if (start > end) {
            throw new IllegalArgumentException("Start must not be greater than end");
        }
        if (start < 0) {
            throw new IllegalArgumentException("Range must be non-negative");
        }

        List<Long> primes = new ArrayList<>();

        // Handle small ranges directly
        if (end < 2) {
            return primes; // Empty list - no primes
        }

        // Adjust start to begin from 2 if it's lower
        start = Math.max(2, start);

        // For small ranges, use trial division method
        if (end - start < 1000) {
            return primesInRangeTrialDivision(start, end);
        }

        // For larger ranges, use segmented sieve
        return primesInRangeSegmentedSieve(start, end);
    }

    /**
     * Simple trial division method for small ranges.
     */
    private static List<Long> primesInRangeTrialDivision(long start, long end) {
        List<Long> primes = new ArrayList<>();

        for (long num = start; num <= end; num++) {
            if (isPrime(num)) {
                primes.add(num);
            }
        }

        return primes;
    }

    /**
     * Checks if a number is prime using trial division.
     */
    private static boolean isPrime(long n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Segmented Sieve of Eratosthenes for larger ranges.
     * More memory efficient than regular sieve for large ranges.
     */
    private static List<Long> primesInRangeSegmentedSieve(long start, long end) {
        int segmentSize = 32768; // Size of each segment
        List<Long> primes = new ArrayList<>();

        // Step 1: Generate small primes up to sqrt(end)
        List<Integer> smallPrimesList = generateSmallPrimes(end);
        addPrimesInRange(start, smallPrimesList, primes);

        // Step 2: Process segments and collect primes
        sieveSegments(start, end, segmentSize, smallPrimesList, primes);

        return primes;
    }

    // Generates small primes up to sqrt(end)
    private static List<Integer> generateSmallPrimes(long end) {
        int sqrt = (int) Math.sqrt(end);
        BitSet smallPrimes = new BitSet(sqrt + 1);
        smallPrimes.set(2, sqrt + 1); // Set all bits to true initially
        for (int i = 2; i * i <= sqrt; i++) {
            if (smallPrimes.get(i)) {
                for (int j = i * i; j <= sqrt; j += i) {
                    smallPrimes.clear(j);
                }
            }
        }
        List<Integer> smallPrimesList = new ArrayList<>();
        for (int i = 2; i <= sqrt; i++) {
            if (smallPrimes.get(i)) {
                smallPrimesList.add(i);
            }
        }
        return smallPrimesList;
    }

    // Add small primes in range to the result
    private static void addPrimesInRange(long start, List<Integer> smallPrimesList, List<Long> primes) {
        for (Integer prime : smallPrimesList) {
            if (prime >= start) {
                primes.add((long) prime);
            }
        }
    }

    // Processes segments and collects primes from them
    private static void sieveSegments(long start, long end, int segmentSize, List<Integer> smallPrimesList, List<Long> primes) {
        long low = Math.max(start, (long) Math.sqrt(end) + 1);
        BitSet segment = new BitSet(segmentSize);

        while (low <= end) {
            long high = Math.min(low + segmentSize - 1, end);
            segment.set(0, (int) (high - low + 1)); // Set all bits to true initially
            sieveSegment(low, high, smallPrimesList, segment);
            collectPrimesFromSegment(low, high, segment, primes);
            low += segmentSize;
        }
    }

    // Sieves a single segment using small primes
    private static void sieveSegment(long low, long high, List<Integer> smallPrimesList, BitSet segment) {
        for (Integer prime : smallPrimesList) {
            long firstMultiple = Math.max((long) prime * prime, (low + prime - 1) / prime * prime);
            for (long j = firstMultiple; j <= high; j += prime) {
                segment.clear((int) (j - low));
            }
        }
    }

    // Collect primes from the sieved segment
    private static void collectPrimesFromSegment(long low, long high, BitSet segment, List<Long> primes) {
        for (int i = 0; i < high - low + 1; i++) {
            if (segment.get(i)) {
                primes.add(low + i);
            }
        }
    }

    /**
     * Returns count of prime numbers in the range [start, end] inclusive.
     * More efficient than generating the full list when only count is needed.
     *
     * @param start the lower bound of the range (inclusive)
     * @param end   the upper bound of the range (inclusive)
     * @return count of prime numbers in the range
     */
    public static long countPrimesInRange(long start, long end) {
        if (start > end) {
            throw new IllegalArgumentException("Start must not be greater than end");
        }
        if (start < 0) {
            throw new IllegalArgumentException("Range must be non-negative");
        }

        // For small ranges, just count from the list
        if (end - start < 1000) {
            return primesInRange(start, end).size();
        }

        // For larger ranges, count during sieve without storing
        return countPrimesSegmentedSieve(start, end);
    }

    /**
     * Counts primes using segmented sieve without storing them.
     */
    private static long countPrimesSegmentedSieve(long start, long end) {
        int segmentSize = 32768;

        // Step 1: Calculate all small primes up to sqrt(end)
        int sqrt = (int) Math.sqrt(end);
        BitSet smallPrimes = generateSmallPrimes(sqrt);

        // Step 2: Convert small primes into a list, and count primes in the range of [start, end]
        List<Integer> smallPrimesList = new ArrayList<>();
        long count = collectSmallPrimesAndCountInRange(start, end, sqrt, smallPrimes, smallPrimesList);

        // Step 3: Process larger primes in segments
        long low = Math.max(start, sqrt + 1L);
        count += countPrimesInLargeSegments(end, segmentSize, smallPrimesList, low);

        return count;
    }

    private static BitSet generateSmallPrimes(int limit) {
        BitSet primes = new BitSet(limit + 1);
        primes.set(2, limit + 1); // Assume all numbers >= 2 are primes

        for (int i = 2; i * i <= limit; i++) {
            if (primes.get(i)) {
                for (int j = i * i; j <= limit; j += i) {
                    primes.clear(j);
                }
            }
        }
        return primes;
    }

    private static long collectSmallPrimesAndCountInRange(
            long start, long end, int sqrt, BitSet smallPrimes, List<Integer> smallPrimesList) {
        long count = 0;
        for (int i = 2; i <= sqrt; i++) {
            if (smallPrimes.get(i)) {
                smallPrimesList.add(i);
                // Check if the prime is within the range [start, end]
                if (i >= start && i <= end) {
                    count++;
                }
            }
        }
        return count;
    }

    private static long countPrimesInLargeSegments(
            long end, int segmentSize, List<Integer> smallPrimesList, long low) {
        long count = 0;
        BitSet segment = new BitSet(segmentSize);

        while (low <= end) {
            // Determine the high boundary for current segment
            long high = Math.min(low + segmentSize - 1, end);

            // Calculate segment length safely
            int segmentLength = (int) Math.min(Integer.MAX_VALUE, high - low + 1);

            // Mark all numbers in the segment as prime initially
            segment.clear();  // Reset the segment
            segment.set(0, segmentLength);

            // Eliminate non-primes using small primes
            for (Integer prime : smallPrimesList) {
                markNonPrimesInSegment(segment, prime, low, high);
            }

            // Count the remaining primes in this segment
            count += segment.cardinality();
            low += segmentSize;
        }
        return count;
    }

    private static void markNonPrimesInSegment(BitSet segment, int prime, long low, long high) {
        long firstMultiple = Math.max((long) prime * prime, (low + prime - 1) / prime * prime);
        for (long j = firstMultiple; j <= high; j += prime) {
            segment.clear((int) (j - low));
        }
    }
}
