package org.nintynine.problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MathP36Test {

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of(1L, List.of()),
                Arguments.of(2L, List.of(
                        new MathP36.PrimeFactor(2, 1))),
                Arguments.of(4L, List.of(
                        new MathP36.PrimeFactor(2, 2))),
                Arguments.of(8L, List.of(
                        new MathP36.PrimeFactor(2, 3))),
                Arguments.of(9L, List.of(
                        new MathP36.PrimeFactor(3, 2))),
                Arguments.of(12L, List.of(
                        new MathP36.PrimeFactor(2, 2),
                        new MathP36.PrimeFactor(3, 1))),
                Arguments.of(315L, List.of(
                        new MathP36.PrimeFactor(3, 2),
                        new MathP36.PrimeFactor(5, 1),
                        new MathP36.PrimeFactor(7, 1))),
                Arguments.of(1024L, List.of(
                        new MathP36.PrimeFactor(2, 10))),
                Arguments.of(123456L, List.of(
                        new MathP36.PrimeFactor(2, 6),
                        new MathP36.PrimeFactor(3, 1),
                        new MathP36.PrimeFactor(643, 1)))
        );
    }

    @ParameterizedTest(name = "Prime factorization of {0}")
    @MethodSource("provideTestCases")
    void testPrimeFactorsMult(long input, List<MathP36.PrimeFactor> expected) {
        assertEquals(expected, MathP36.primeFactorsMult(input));
    }

    @Test
    @DisplayName("Test invalid inputs")
    void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> MathP36.primeFactorsMult(0));
        assertThrows(IllegalArgumentException.class, () -> MathP36.primeFactorsMult(-1));
        assertThrows(IllegalArgumentException.class, () -> MathP36.primeFactorsMult(-315));
    }

    @Test
    @DisplayName("Test reconstruction of original number")
    void testReconstruction() {
        long[] testNumbers = {1L, 2L, 4L, 8L, 9L, 12L, 315L, 1024L, 123456L};

        for (long n : testNumbers) {
            List<MathP36.PrimeFactor> factors = MathP36.primeFactorsMult(n);
            assertEquals(n, MathP36.reconstruct(factors),
                    "Reconstruction should match original number for " + n);
        }
    }

    @Test
    @DisplayName("Test reconstruction with large numbers")
    void testReconstructionLarge() {
        long n = 1_000_000_000_000L;
        List<MathP36.PrimeFactor> factors = MathP36.primeFactorsMult(n);
        assertEquals(n, MathP36.reconstruct(factors),
                "Reconstruction should match original number for large input");
    }

    @Test
    @DisplayName("Verify factors are prime")
    void verifyFactorsArePrime() {
        long[] testNumbers = {12L, 315L, 1001L, 1024L, 123456L};

        for (long n : testNumbers) {
            List<MathP36.PrimeFactor> factors = MathP36.primeFactorsMult(n);
            assertTrue(factors.stream()
                            .allMatch(f -> MathP31.isPrime(f.factor())),
                    "All factors should be prime for " + n);
        }
    }

    @Test
    @DisplayName("Verify factors are in ascending order")
    void verifyFactorsAreOrdered() {
        long[] testNumbers = {12L, 315L, 1001L, 1024L, 123456L};

        for (long n : testNumbers) {
            List<MathP36.PrimeFactor> factors = MathP36.primeFactorsMult(n);
            assertTrue(isAscending(factors),
                    "Factors should be in ascending order for " + n);
        }
    }

    private boolean isAscending(List<MathP36.PrimeFactor> factors) {
        for (int i = 1; i < factors.size(); i++) {
            if (factors.get(i).factor() <= factors.get(i-1).factor()) {
                return false;
            }
        }
        return true;
    }

    @Test
    @DisplayName("Test toString representation")
    void testToString() {
        List<MathP36.PrimeFactor> factors = MathP36.primeFactorsMult(12);
        assertEquals("(2 2)", factors.get(0).toString());
        assertEquals("(3 1)", factors.get(1).toString());
    }
}
