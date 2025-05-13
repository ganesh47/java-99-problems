package org.nintynine.problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MathP38Test {

    @Test
    @DisplayName("Compare methods for φ(10090)")
    void testCompare10090() {
        List<MathP38.PerformanceMetrics> results = MathP38.compare(10090);

        // Verify both methods give same result
        assertEquals(results.get(0).result(), results.get(1).result(),
                "Both methods should give the same result");

        // Print detailed comparison
        System.out.println("Performance comparison for φ(10090):");
        results.forEach(System.out::println);

        // Improved method should have fewer operations
        assertTrue(results.get(1).arithmeticOps() < results.get(0).arithmeticOps(),
                "Improved method should use fewer arithmetic operations");
        assertTrue(results.get(1).comparisonOps() < results.get(0).comparisonOps(),
                "Improved method should use fewer comparisons");
    }

    @Test
    @DisplayName("Compare methods for various numbers")
    void testCompareVariousNumbers() {
        long[] numbers = {100, 1000, 10090, 12345};

        for (long n : numbers) {
            List<MathP38.PerformanceMetrics> results = MathP38.compare(n);

            System.out.printf("%nPerformance comparison for φ(%d):%n", n);
            results.forEach(System.out::println);

            // Verify results match
            assertEquals(results.get(0).result(), results.get(1).result(),
                    "Results should match for n = " + n);

            // Verify improved method is more efficient
            assertTrue(results.get(1).timeNanos() < results.get(0).timeNanos(),
                    "Improved method should be faster for n = " + n);
        }
    }

    @Test
    @DisplayName("Test operation counting accuracy")
    void testOperationCounting() {
        // Test with small numbers where we can verify operation counts
        List<MathP38.PerformanceMetrics> results = MathP38.compare(12);

        // Primitive method should have significant operations
        assertTrue(results.get(0).arithmeticOps() > 20,
                "Primitive method should have counted arithmetic operations");
        assertTrue(results.get(0).comparisonOps() > 10,
                "Primitive method should have counted comparisons");

        // Improved method should have fewer operations
        assertTrue(results.get(1).arithmeticOps() < results.get(0).arithmeticOps(),
                "Improved method should use fewer arithmetic operations");
    }
}
