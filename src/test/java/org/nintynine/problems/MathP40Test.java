package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MathP40Test {

  @Test
  @DisplayName("Test example case: 28 = 5 + 23")
  void testExample() {
    Optional<MathP40.GoldbachPair> result = MathP40.goldbach(28);
    assertTrue(result.isPresent());
    assertEquals(5, result.get().prime1());
    assertEquals(23, result.get().prime2());
  }

  @Test
  @DisplayName("Test small even numbers")
  void testSmallEvenNumbers() {
    // 4 = 2 + 2
    Optional<MathP40.GoldbachPair> result = MathP40.goldbach(4);
    assertTrue(result.isPresent());
    assertEquals(2, result.get().prime1());
    assertEquals(2, result.get().prime2());

    // 6 = 3 + 3
    result = MathP40.goldbach(6);
    assertTrue(result.isPresent());
    assertEquals(3, result.get().prime1());
    assertEquals(3, result.get().prime2());

    // 8 = 3 + 5
    result = MathP40.goldbach(8);
    assertTrue(result.isPresent());
    assertEquals(3, result.get().prime1());
    assertEquals(5, result.get().prime2());
  }

  @Test
  @DisplayName("Test invalid inputs")
  void testInvalidInputs() {
    // Test numbers <= 2
    assertThrows(IllegalArgumentException.class, () -> MathP40.goldbach(2));
    assertThrows(IllegalArgumentException.class, () -> MathP40.goldbach(1));
    assertThrows(IllegalArgumentException.class, () -> MathP40.goldbach(0));
    assertThrows(IllegalArgumentException.class, () -> MathP40.goldbach(-4));

    // Test odd numbers
    assertThrows(IllegalArgumentException.class, () -> MathP40.goldbach(7));
    assertThrows(IllegalArgumentException.class, () -> MathP40.goldbach(15));
  }

  @Test
  @DisplayName("Test finding all Goldbach pairs")
  void testGoldbachAll() {
    // Test 10 = 3 + 7 = 5 + 5
    MathP40.GoldbachPair[] pairs = MathP40.goldbachAll(10);
    assertEquals(2, pairs.length);
    assertEquals(3, pairs[0].prime1());
    assertEquals(7, pairs[0].prime2());
    assertEquals(5, pairs[1].prime1());
    assertEquals(5, pairs[1].prime2());

    // Test 20 = 3 + 17 = 7 + 13
    pairs = MathP40.goldbachAll(20);
    assertEquals(2, pairs.length);
    assertEquals(3, pairs[0].prime1());
    assertEquals(17, pairs[0].prime2());
    assertEquals(7, pairs[1].prime1());
    assertEquals(13, pairs[1].prime2());
  }

  @ParameterizedTest
  @DisplayName("Verify Goldbach's conjecture up to various limits")
  @ValueSource(ints = {100, 500, 1000})
  void testVerifyGoldbachUpTo(int limit) {
    assertTrue(
        MathP40.verifyGoldbachUpTo(limit), "Goldbach's conjecture should hold up to " + limit);
  }

  @Test
  @DisplayName("Test GoldbachPair properties")
  void testGoldbachPairProperties() {
    Optional<MathP40.GoldbachPair> result = MathP40.goldbach(28);
    assertTrue(result.isPresent());
    MathP40.GoldbachPair pair = result.get();

    // Test sum is correct
    assertEquals(28, pair.prime1() + pair.prime2());

    // Test both numbers are prime
    assertTrue(MathP31.isPrime(pair.prime1()));
    assertTrue(MathP31.isPrime(pair.prime2()));

    // Test toString format
    assertEquals("(5 23)", pair.toString());

    // Test isValid
    assertTrue(pair.isValid());
  }

  @Test
  @DisplayName("Test larger even numbers")
  void testLargerEvenNumbers() {
    long[] numbers = {100, 200, 500, 1000};

    for (long n : numbers) {
      Optional<MathP40.GoldbachPair> result = MathP40.goldbach(n);
      assertTrue(result.isPresent(), "Should find Goldbach pair for " + n);

      MathP40.GoldbachPair pair = result.get();
      assertEquals(n, pair.prime1() + pair.prime2(), "Sum should equal original number");
      assertTrue(pair.isValid(), "Should be valid Goldbach pair");
    }
  }

  @Test
  @DisplayName("Test performance")
  void testPerformance() {
    long startTime = System.nanoTime();

    // Verify conjecture up to 10000
    assertTrue(MathP40.verifyGoldbachUpTo(10000));

    long endTime = System.nanoTime();
    double seconds = (endTime - startTime) / 1_000_000_000.0;

    System.out.printf("Verified Goldbach's conjecture up to 10000 in %.3f seconds%n", seconds);

    // Should complete in reasonable time
    assertTrue(seconds < 1.0, "Verification should complete in less than 1 second");
  }
}
