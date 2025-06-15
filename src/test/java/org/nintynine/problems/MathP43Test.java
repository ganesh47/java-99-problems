package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MathP43Test {

  @BeforeEach
  void setUp() {
    MathP43.clearCache();
  }

  @Test
  @DisplayName("Test 1-bit Gray code")
  void testOneBitGrayCode() {
    List<String> result = MathP43.gray(1);
    assertEquals(List.of("0", "1"), result);
  }

  @Test
  @DisplayName("Test 2-bit Gray code")
  void testTwoBitGrayCode() {
    List<String> result = MathP43.gray(2);
    assertEquals(List.of("00", "01", "11", "10"), result);
  }

  @Test
  @DisplayName("Test 3-bit Gray code")
  void testThreeBitGrayCode() {
    List<String> result = MathP43.gray(3);
    assertEquals(List.of("000", "001", "011", "010", "110", "111", "101", "100"), result);
  }

  @Test
  @DisplayName("Test invalid input")
  void testInvalidInput() {
    assertThrows(IllegalArgumentException.class, () -> MathP43.gray(0));
    assertThrows(IllegalArgumentException.class, () -> MathP43.gray(-1));
  }

  @Test
  @DisplayName("Test Gray code properties")
  void testGrayCodeProperties() {
    List<String> codes = MathP43.gray(4);

    // Size should be 2^n
    assertEquals(16, codes.size());

    // Adjacent codes should differ by exactly one bit
    for (int i = 0; i < codes.size() - 1; i++) {
      assertEquals(
          1,
          hammingDistance(codes.get(i), codes.get(i + 1)),
          "Adjacent codes should differ by exactly one bit");
    }

    // The first and last codes should differ by one bit
    assertEquals(
        1,
        hammingDistance(codes.getFirst(), codes.getLast()),
        "First and last codes should differ by exactly one bit");
  }

  @Test
  @DisplayName("Test caching mechanism")
  void testCaching() {
    assertEquals(0, MathP43.getCacheSize());

    MathP43.gray(3);
    assertEquals(3, MathP43.getCacheSize()); // Should cache n=1,2,3

    MathP43.gray(3); // Should use cache
    assertEquals(3, MathP43.getCacheSize());

    MathP43.gray(4);
    assertEquals(4, MathP43.getCacheSize());
  }

  @ParameterizedTest(name = "Test Gray code size for {0} bits")
  @ValueSource(ints = {1, 2, 3, 4, 5})
  void testGrayCodeSize(int n) {
    List<String> codes = MathP43.gray(n);
    assertEquals(1 << n, codes.size(), "Gray code size should be 2^n");
  }

  // Helper method to calculate Hamming distance between two binary strings
  private int hammingDistance(String s1, String s2) {
    int distance = 0;
    for (int i = 0; i < s1.length(); i++) {
      if (s1.charAt(i) != s2.charAt(i)) {
        distance++;
      }
    }
    return distance;
  }
}
