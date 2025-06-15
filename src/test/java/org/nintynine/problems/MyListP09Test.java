package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for MyListP09's consecutive duplicate packing functionality. Verifies the pack()
 * method behavior under various scenarios.
 */
class MyListP09Test {

  /**
   * Tests the basic packing functionality with a sequence containing multiple consecutive
   * duplicates.
   */
  @Test
  void testBasicPacking() {
    MyListP09<String> list =
        new MyListP09<>("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e");
    MyListP09<MyListP09<String>> packed = list.pack();

    assertEquals(6, packed.length());
    assertArrayEquals(new String[] {"a", "a", "a", "a"}, packed.elementAt(1).items);
    assertArrayEquals(new String[] {"b"}, packed.elementAt(2).items);
    assertArrayEquals(new String[] {"c", "c"}, packed.elementAt(3).items);
    assertArrayEquals(new String[] {"a", "a"}, packed.elementAt(4).items);
    assertArrayEquals(new String[] {"d"}, packed.elementAt(5).items);
    assertArrayEquals(new String[] {"e", "e", "e", "e"}, packed.elementAt(1 + 5).items);
  }

  /**
   * Tests packing behavior when the input list has no consecutive duplicates. Each element should
   * be in its own sublist.
   */
  @Test
  void testNoConsecutiveDuplicates() {
    MyListP09<String> list = new MyListP09<>("a", "b", "c", "d");
    MyListP09<MyListP09<String>> packed = list.pack();

    assertEquals(4, packed.length());
    assertArrayEquals(new String[] {"a"}, packed.elementAt(1).items);
    assertArrayEquals(new String[] {"b"}, packed.elementAt(2).items);
    assertArrayEquals(new String[] {"c"}, packed.elementAt(3).items);
    assertArrayEquals(new String[] {"d"}, packed.elementAt(4).items);
  }

  /**
   * Tests packing when all elements in the list are identical. Should result in a single sublist
   * containing all elements.
   */
  @Test
  void testAllDuplicates() {
    MyListP09<String> list = new MyListP09<>("a", "a", "a", "a");
    MyListP09<MyListP09<String>> packed = list.pack();

    assertEquals(1, packed.length());
    assertArrayEquals(new String[] {"a", "a", "a", "a"}, packed.elementAt(1).items);
  }

  /** Tests packing behavior with an empty list. Should return an empty list. */
  @Test
  void testEmptyList() {
    MyListP09<String> list = new MyListP09<>();
    MyListP09<MyListP09<String>> packed = list.pack();

    assertEquals(0, packed.length());
  }

  /**
   * Tests packing behavior with a single-element list. Should return a list containing one sublist
   * with one element.
   */
  @Test
  void testSingleElement() {
    MyListP09<String> list = new MyListP09<>("a");
    MyListP09<MyListP09<String>> packed = list.pack();

    assertEquals(1, packed.length());
    assertArrayEquals(new String[] {"a"}, packed.elementAt(1).items);
  }

  /**
   * Tests packing behavior with numeric values. Verifies that packing works correctly with Integer
   * objects.
   */
  @Test
  void testWithNumbers() {
    MyListP09<Integer> list = new MyListP09<>(1, 1, 1, 2, 3, 3, 4, 4, 4, 4);
    MyListP09<MyListP09<Integer>> packed = list.pack();

    assertEquals(4, packed.length());
    assertArrayEquals(new Integer[] {1, 1, 1}, packed.elementAt(1).items);
    assertArrayEquals(new Integer[] {2}, packed.elementAt(1 + 1).items);
    assertArrayEquals(new Integer[] {3, 3}, packed.elementAt(1 + 2).items);
    assertArrayEquals(new Integer[] {4, 4, 4, 4}, packed.elementAt(1 + 3).items);
  }

  /**
   * Tests packing behavior with alternating elements. Each element should be in its own sublist.
   */
  @Test
  void testAlternatingElements() {
    MyListP09<String> list = new MyListP09<>("a", "b", "a", "b", "a", "b");
    MyListP09<MyListP09<String>> packed = list.pack();

    assertEquals(6, packed.length());
    for (int i = 0; i < packed.length(); i++) {
      assertArrayEquals(new String[] {i % 2 == 0 ? "a" : "b"}, packed.elementAt(1 + i).items);
    }
  }

  /**
   * Tests that the pack method throws NullPointerException when the list contains null elements.
   */
  @Test
  void testWithNulls() {
    MyListP09<String> list = new MyListP09<>(null, null, "a", "a", null);
    assertThrows(NullPointerException.class, list::pack);
  }
}
