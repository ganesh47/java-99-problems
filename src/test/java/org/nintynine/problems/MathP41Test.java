package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MathP41Test {

  @Test
  @DisplayName("Test example range 9 to 20")
  void testExampleRange() {
    List<MathP41.GoldbachListEntry> result = MathP41.goldbachList(9, 20);

    // Verify specific compositions
    assertEquals(6, result.size());
    assertEquals("10 = 3 + 7", result.get(0).toString());
    assertEquals("12 = 5 + 7", result.get(1).toString());
    assertEquals("14 = 3 + 11", result.get(2).toString());
    assertEquals("16 = 3 + 13", result.get(3).toString());
    assertEquals("18 = 5 + 13", result.get(4).toString());
    assertEquals("20 = 3 + 17", result.get(5).toString());
  }

  @Test
  @DisplayName("Test compositions with minimum prime 50")
  void testLargePrimeCompositions() {
    List<MathP41.GoldbachListEntry> result = MathP41.goldbachList(1, 2000, 50);

    // Print results
    System.out.println("Goldbach compositions with primes > 50:");
    System.out.println(MathP41.formatGoldbachList(result));

    // Verify all compositions have primes > 50
    result.forEach(
        entry -> {
          assertTrue(entry.pair().prime1() > 50, "First prime should be > 50");
          assertTrue(entry.pair().prime2() > 50, "Second prime should be > 50");
        });
  }

  @Test
  @DisplayName("Test invalid inputs")
  void testInvalidInputs() {
    assertThrows(IllegalArgumentException.class, () -> MathP41.goldbachList(20, 10));
    assertThrows(IllegalArgumentException.class, () -> MathP41.goldbachList(-5, 10));
    assertThrows(IllegalArgumentException.class, () -> MathP41.goldbachList(10, 20, -1));
  }

  @Test
  @DisplayName("Test count of rare compositions")
  void testRareCompositions() {
    long count = MathP41.countGoldbachCompositions(2, 3000, 50);
    System.out.printf(
        "Found %d Goldbach compositions with both primes > 50 " + "in range 2..3000%n", count);

    // There should be relatively few such compositions
    assertFalse(count < 100, "Should be relatively few compositions with both primes > 50");
  }

  @Test
  @DisplayName("Test empty ranges")
  void testEmptyRanges() {
    // Range below 4 should be empty (no even numbers that are sums of primes)
    assertTrue(MathP41.goldbachList(1, 3).isEmpty());

    // Range with minimum prime too high for the numbers
    assertTrue(MathP41.goldbachList(10, 20, 15).isEmpty());
  }

  @Test
  @DisplayName("Test continuous range verification")
  void testContinuousRange() {
    List<MathP41.GoldbachListEntry> result = MathP41.goldbachList(4, 30);

    // Verify each even number has a composition
    for (long i = 4; i <= 30; i += 2) {
      final long n = i;
      assertTrue(
          result.stream().anyMatch(entry -> entry.number() == n),
          "Should find composition for " + n);
    }
  }

  @Test
  @DisplayName("Test formatting")
  void testFormatting() {
    List<MathP41.GoldbachListEntry> result = MathP41.goldbachList(10, 14);
    String formatted = MathP41.formatGoldbachList(result);

    assertEquals(
        """
        10 = 3 + 7
        12 = 5 + 7
        14 = 3 + 11""",
        formatted);
  }
}
