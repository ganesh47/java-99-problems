package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MathP35Test {

  private static Stream<Arguments> provideTestCases() {
    return Stream.of(
        Arguments.of(1L, List.of()), // Special case: 1
        Arguments.of(2L, List.of(2L)), // Prime number
        Arguments.of(3L, List.of(3L)), // Prime number
        Arguments.of(4L, List.of(2L, 2L)), // Power of prime
        Arguments.of(6L, List.of(2L, 3L)), // Product of primes
        Arguments.of(8L, List.of(2L, 2L, 2L)), // Power of 2
        Arguments.of(9L, List.of(3L, 3L)), // Perfect square
        Arguments.of(10L, List.of(2L, 5L)), // Product of primes
        Arguments.of(12L, List.of(2L, 2L, 3L)), // Multiple factors
        Arguments.of(315L, List.of(3L, 3L, 5L, 7L)), // Example from problem
        Arguments.of(330L, List.of(2L, 3L, 5L, 11L)), // Multiple different primes
        Arguments.of(1001L, List.of(7L, 11L, 13L)), // Large number
        Arguments.of(1024L, List.of(2L, 2L, 2L, 2L, 2L, 2L, 2L, 2L, 2L, 2L)) // Power of 2
        );
  }

  @ParameterizedTest(name = "Prime factors of {0} should be {1}")
  @MethodSource("provideTestCases")
  void testPrimeFactors(long input, List<Long> expected) {
    assertEquals(expected, MathP35.primeFactors(input));
  }

  @Test
  @DisplayName("Test invalid inputs")
  void testInvalidInputs() {
    assertThrows(IllegalArgumentException.class, () -> MathP35.primeFactors(0));
    assertThrows(IllegalArgumentException.class, () -> MathP35.primeFactors(-1));
    assertThrows(IllegalArgumentException.class, () -> MathP35.primeFactors(-315));
  }

  @Test
  @DisplayName("Test large numbers")
  void testLargeNumbers() {
    // Test a large number: 123456 = 2^4 * 3 * 643
    assertEquals(List.of(2L, 2L, 2L, 2L, 2L, 2L, 3L, 643L), MathP35.primeFactors(123456L));

    // Test a large prime number
    assertEquals(List.of(104729L), MathP35.primeFactors(104729L));
  }

  @Test
  @DisplayName("Verify prime factorization product")
  void verifyFactorizationProduct() {
    // Test that multiplying all factors gives back the original number
    long[] testNumbers = {12L, 315L, 1001L, 1024L, 123456L};

    for (long n : testNumbers) {
      List<Long> factors = MathP35.primeFactors(n);
      long product = factors.stream().reduce(1L, (a, b) -> a * b);
      assertEquals(n, product, "Product of prime factors should equal original number for " + n);
    }
  }

  @Test
  @DisplayName("Verify factors are prime")
  void verifyFactorsArePrime() {
    long[] testNumbers = {12L, 315L, 1001L, 1024L, 123456L};

    for (long n : testNumbers) {
      List<Long> factors = MathP35.primeFactors(n);
      assertTrue(
          factors.stream().allMatch(MathP31::isPrime), "All factors should be prime for " + n);
    }
  }

  @Test
  @DisplayName("Verify factors are in ascending order")
  void verifyFactorsAreOrdered() {
    long[] testNumbers = {12L, 315L, 1001L, 1024L, 123456L};

    for (long n : testNumbers) {
      List<Long> factors = MathP35.primeFactors(n);
      assertTrue(isAscending(factors), "Factors should be in ascending order for " + n);
    }
  }

  private boolean isAscending(List<Long> numbers) {
    for (int i = 1; i < numbers.size(); i++) {
      if (numbers.get(i) < numbers.get(i - 1)) {
        return false;
      }
    }
    return true;
  }
}
