package org.nintynine.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * P43: Generate N-bit Gray codes
 * A Gray code is a sequence where consecutive values differ in only one bit position.
 */
public class MathP43 {
    // Cache to store previously calculated Gray codes
    private static final ConcurrentHashMap<Integer, List<String>> grayCodeCache =
            new ConcurrentHashMap<>();

    private MathP43() {} // Prevent instantiation

    /**
     * Generates N-bit Gray code sequence.
     * Uses caching to improve performance for repeated calls.
     *
     * @param n number of bits (must be positive)
     * @return List of strings representing the Gray code sequence
     * @throws IllegalArgumentException if n is less than 1
     */
    public static List<String> gray(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Number of bits must be positive");
        }

        // Check cache first
        return grayCodeCache.computeIfAbsent(n, MathP43::generateGrayCode);
    }

    /**
     * Internal method to generate Gray code without caching.
     * Uses recursive approach with concatenation.
     */
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private static List<String> generateGrayCode(int n) {
        if (n == 1) {
            return List.of("0", "1");
        }

        // Get the n-1 bit Gray code
        List<String> prevGray = gray(n - 1);

        // Create a result list with known capacity
        List<String> result = new ArrayList<>(1 << n);

        // Add a forward sequence with the leading 0
        for (String code : prevGray) {
            result.add("0" + code);
        }

        // Add a reverse sequence with leading 1
        for (int i = prevGray.size() - 1; i >= 0; i--) {
            result.add("1" + prevGray.get(i));
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Clears the internal cache.
     * Useful for testing or when memory needs to be freed.
     */
    static void clearCache() {
        grayCodeCache.clear();
    }

    /**
     * Returns the current size of the cache.
     * Useful for testing and monitoring.
     */
    static int getCacheSize() {
        return grayCodeCache.size();
    }
}
