package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MathP33Test {

  @ParameterizedTest(name = "{0} and {1} should be coprime")
  @DisplayName("Test known coprime numbers")
  @CsvSource({
    "35, 64", // Example from the problem statement
    "21, 22", // Consecutive numbers
    "15, 28", // Regular coprime numbers
    "7, 13", // Two prime numbers
    "1, 17", // One is 1 (coprime with any number)
    "17, 1", // Reverse order of above
    "1, 1", // Special case - both are 1
    "49, 50", // Square number with its consecutive
    "27, 35", // Two composite numbers that are coprime
    "8, 15", // Powers of different primes
    "125, 128" // Higher powers of different primes
  })
  void testCoprimeNumbers(long a, long b) {
    assertTrue(MathP33.areCoprime(a, b));
    assertTrue(MathP33.areCoprime(b, a)); // Test commutative property
  }

  @ParameterizedTest(name = "{0} and {1} should not be coprime")
  @DisplayName("Test known non-coprime numbers")
  @CsvSource({
    "25, 35", // Share factor 5
    "14, 28", // One is multiple of other
    "15, 45", // Share multiple factors
    "13, 13", // Same number (except 1)
    "16, 64", // Share power of 2
    "21, 63", // Share factor 3
    "100, 150", // Share multiple factors (2 and 5)
    "36, 48", // Share multiple factors (2 and 3)
    "49, 147", // Share factor 7
    "121, 363" // Share factor 11
  })
  void testNonCoprimeNumbers(long a, long b) {
    assertFalse(MathP33.areCoprime(a, b));
    assertFalse(MathP33.areCoprime(b, a)); // Test commutative property
  }

  @Test
  @DisplayName("Test large coprime numbers")
  void testLargeCoprimeNumbers() {
    assertFalse(MathP33.areCoprime(123456789, 987654321));
    assertTrue(MathP33.areCoprime(104729, 104723)); // Two large consecutive primes
  }

  @Test
  @DisplayName("Test large non-coprime numbers")
  void testLargeNonCoprimeNumbers() {
    assertFalse(MathP33.areCoprime(123456, 654321)); // Share common factors
    assertFalse(MathP33.areCoprime(1000000, 2000000)); // Share large power of 10
  }

  @Test
  @DisplayName("Test negative numbers throw exception")
  void testNegativeNumbers() {
    assertThrows(IllegalArgumentException.class, () -> MathP33.areCoprime(-35, 64));
    assertThrows(IllegalArgumentException.class, () -> MathP33.areCoprime(35, -64));
    assertThrows(IllegalArgumentException.class, () -> MathP33.areCoprime(-35, -64));
  }

  @Test
  @DisplayName("Test zero throws exception")
  void testZero() {
    assertThrows(IllegalArgumentException.class, () -> MathP33.areCoprime(0, 64));
    assertThrows(IllegalArgumentException.class, () -> MathP33.areCoprime(35, 0));
    assertThrows(IllegalArgumentException.class, () -> MathP33.areCoprime(0, 0));
  }

  @Test
  @DisplayName("Test special mathematical properties")
  void testSpecialProperties() {
    // Property 1: Consecutive numbers are always coprime
    assertTrue(MathP33.areCoprime(100, 101));
    assertTrue(MathP33.areCoprime(999, 1000));

    // Property 2: Any number is coprime with 1
    assertTrue(MathP33.areCoprime(1, 999999));
    assertTrue(MathP33.areCoprime(999999, 1));

    // Property 3: Two different prime numbers are always coprime
    assertTrue(MathP33.areCoprime(17, 19));
    assertTrue(MathP33.areCoprime(101, 103));

    // Property 4: If a number is coprime to each of two numbers, it's not necessarily
    // coprime to their product
    assertTrue(MathP33.areCoprime(10, 21));
    assertFalse(MathP33.areCoprime(10, 28));
    assertFalse(MathP33.areCoprime(10, 21 * 28));
  }
}
