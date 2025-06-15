package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/** Test class for MyListP22's range functionality. */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP22Test {

  /** Tests basic ascending range. */
  @Test
  void testAscendingRange() {
    MyListP22<Integer> result = MyListP22.range(4, 9);

    assertEquals(6, result.length());
    assertEquals(4, result.elementAt(1 + 0));
    assertEquals(Integer.valueOf(5), result.elementAt(1 + 1));
    assertEquals(Integer.valueOf(6), result.elementAt(1 + 2));
    assertEquals(Integer.valueOf(7), result.elementAt(1 + 3));
    assertEquals(Integer.valueOf(8), result.elementAt(1 + 4));
    assertEquals(Integer.valueOf(9), result.elementAt(1 + 5));
  }

  /** Tests basic descending range. */
  @Test
  void testDescendingRange() {
    MyListP22<Integer> result = MyListP22.range(9, 4);

    assertEquals(6, result.length());
    assertEquals(Integer.valueOf(9), result.elementAt(1 + 0));
    assertEquals(Integer.valueOf(8), result.elementAt(1 + 1));
    assertEquals(Integer.valueOf(7), result.elementAt(1 + 2));
    assertEquals(Integer.valueOf(6), result.elementAt(1 + 3));
    assertEquals(Integer.valueOf(5), result.elementAt(1 + 4));
    assertEquals(Integer.valueOf(4), result.elementAt(1 + 5));
  }

  /** Tests single number range. */
  @Test
  void testSingleNumberRange() {
    MyListP22<Integer> result = MyListP22.range(3, 3);

    assertEquals(1, result.length());
    assertEquals(Integer.valueOf(3), result.elementAt(1 + 0));
  }

  /** Tests negative numbers range. */
  @Test
  void testNegativeRange() {
    MyListP22<Integer> result = MyListP22.range(-3, 2);

    assertEquals(6, result.length());
    assertEquals(Integer.valueOf(-3), result.elementAt(1 + 0));
    assertEquals(Integer.valueOf(-2), result.elementAt(1 + 1));
    assertEquals(Integer.valueOf(-1), result.elementAt(1 + 2));
    assertEquals(Integer.valueOf(0), result.elementAt(1 + 3));
    assertEquals(Integer.valueOf(1), result.elementAt(1 + 4));
    assertEquals(Integer.valueOf(2), result.elementAt(1 + 5));
  }

  /** Tests descending negative numbers range. */
  @Test
  void testDescendingNegativeRange() {
    MyListP22<Integer> result = MyListP22.range(2, -3);

    assertEquals(6, result.length());
    assertEquals(Integer.valueOf(2), result.elementAt(1 + 0));
    assertEquals(Integer.valueOf(1), result.elementAt(1 + 1));
    assertEquals(Integer.valueOf(0), result.elementAt(1 + 2));
    assertEquals(Integer.valueOf(-1), result.elementAt(1 + 3));
    assertEquals(Integer.valueOf(-2), result.elementAt(1 + 4));
    assertEquals(Integer.valueOf(-3), result.elementAt(1 + 5));
  }

  /** Tests large range size. */
  @Test
  void testLargeRange() {
    MyListP22<Integer> result = MyListP22.range(1, 1000);

    assertEquals(1000, result.length());
    assertEquals(Integer.valueOf(1), result.elementAt(1 + 0));
    assertEquals(Integer.valueOf(1000), result.elementAt(1 + 999));

    // Check some values in between
    assertEquals(Integer.valueOf(500), result.elementAt(1 + 499));
    assertEquals(Integer.valueOf(750), result.elementAt(1 + 749));
  }

  /** Tests range combined with other operations. */
  @Test
  void testRangeWithOperations() {
    MyListP22<Integer> range = MyListP22.range(1, 5);

    // Test insertion
    MyListP21<Integer> withInserted = range.insertAt(99, 3);
    assertEquals(6, withInserted.length());
    assertEquals(Integer.valueOf(99), withInserted.elementAt(1 + 2));

    // Test removal
    MyListP17.Pair<Integer, MyListP20<Integer>> removed = range.removeAt(2);
    assertEquals(Integer.valueOf(2), removed.first());
    assertEquals(4, removed.second().length());

    // Test rotation
    MyListP19<Integer> rotated = range.rotate(2);
    assertEquals(Integer.valueOf(3), rotated.elementAt(1 + 0));
    assertEquals(Integer.valueOf(2), rotated.elementAt(1 + 4));
  }

  /** Tests validation of list contents. */
  @Test
  void testRangeValidation() {
    MyListP22<Integer> ascending = MyListP22.range(1, 5);
    MyListP22<Integer> descending = MyListP22.range(5, 1);

    // Check ascending order
    for (int i = 0; i < ascending.length() - 1; i++) {
      assertTrue(ascending.elementAt(1 + i) < ascending.elementAt(1 + i + 1));
    }

    // Check descending order
    for (int i = 0; i < descending.length() - 1; i++) {
      assertTrue(descending.elementAt(1 + i) > descending.elementAt(1 + i + 1));
    }
  }

  /** Tests consecutive range operations. */
  @Test
  void testConsecutiveRanges() {
    MyListP22<Integer> first = MyListP22.range(1, 3);
    MyListP22<Integer> second = MyListP22.range(3, 1);

    // Combine ranges using existing operations
    MyListP22<Integer> combined =
        new MyListP22<>(
            Stream.concat(Arrays.stream(first.items), Arrays.stream(second.items))
                .toArray(Integer[]::new));

    assertEquals(6, combined.length());
    assertEquals(Integer.valueOf(1), combined.elementAt(1 + 0));
    assertEquals(Integer.valueOf(3), combined.elementAt(1 + 2));
    assertEquals(Integer.valueOf(3), combined.elementAt(1 + 3));
    assertEquals(Integer.valueOf(1), combined.elementAt(1 + 5));
  }

  @Test
  void testBoundaryCases() {
    // Test range crossing zero
    MyListP22<Integer> crossingZero = MyListP22.range(-2, 2);
    assertEquals(5, crossingZero.length());
    assertEquals(Integer.valueOf(-2), crossingZero.elementAt(1 + 0));
    assertEquals(Integer.valueOf(0), crossingZero.elementAt(1 + 2));
    assertEquals(Integer.valueOf(2), crossingZero.elementAt(1 + 4));

    // Test range with maximum gap of 1
    MyListP22<Integer> minGap = MyListP22.range(100, 101);
    assertEquals(2, minGap.length());
    assertEquals(Integer.valueOf(100), minGap.elementAt(1 + 0));
    assertEquals(Integer.valueOf(101), minGap.elementAt(1 + 1));
  }

  /** Tests range with arithmetic operations. */
  @Test
  void testRangeArithmetic() {
    MyListP22<Integer> range = MyListP22.range(1, 5);

    // Calculate sum
    int sum = Arrays.stream(range.items).mapToInt(Integer::intValue).sum();
    assertEquals(15, sum); // 1 + 2 + 3 + 4 + 5

    // Calculate product
    int product = Arrays.stream(range.items).mapToInt(Integer::intValue).reduce(1, (a, b) -> a * b);
    assertEquals(120, product); // 1 * 2 * 3 * 4 * 5
  }
}
