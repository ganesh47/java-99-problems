package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Test class for MyListP13's direct run-length encoding functionality. */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP13Test {
  /** Tests basic direct encoding with a sequence containing both single elements and duplicates. */
  @Test
  void testBasicDirectEncoding() {
    MyListP13<String> list =
        new MyListP13<>("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e");
    MyListP13<Object> encoded = list.encodeDirect();

    assertEquals(6, encoded.length());
    assertEquals(new MyListP10.EncodedElement<>(4L, "a"), encoded.elementAt(1 + 0));
    assertEquals("b", encoded.elementAt(1 + 1));
    assertEquals(new MyListP10.EncodedElement<>(2L, "c"), encoded.elementAt(1 + 2));
    assertEquals(new MyListP10.EncodedElement<>(2L, "a"), encoded.elementAt(1 + 3));
    assertEquals("d", encoded.elementAt(1 + 4));
    assertEquals(new MyListP10.EncodedElement<>(4L, "e"), encoded.elementAt(1 + 5));
  }

  /** Tests encoding when no elements have duplicates. */
  @Test
  void testNoConsecutiveDuplicates() {
    MyListP13<String> list = new MyListP13<>("a", "b", "c", "d");
    MyListP13<Object> encoded = list.encodeDirect();

    assertEquals(4, encoded.length());
    assertEquals("a", encoded.elementAt(1 + 0));
    assertEquals("b", encoded.elementAt(1 + 1));
    assertEquals("c", encoded.elementAt(1 + 2));
    assertEquals("d", encoded.elementAt(1 + 3));
  }

  /** Tests encoding when all elements are identical. */
  @Test
  void testAllDuplicates() {
    MyListP13<String> list = new MyListP13<>("a", "a", "a", "a");
    MyListP13<Object> encoded = list.encodeDirect();

    assertEquals(1, encoded.length());
    assertEquals(new MyListP10.EncodedElement<>(4L, "a"), encoded.elementAt(1 + 0));
  }

  /** Tests encoding of an empty list. */
  @Test
  void testEmptyList() {
    MyListP13<String> list = new MyListP13<>();
    MyListP13<Object> encoded = list.encodeDirect();

    assertEquals(0, encoded.length());
  }

  /** Tests encoding of a single element. */
  @Test
  void testSingleElement() {
    MyListP13<String> list = new MyListP13<>("a");
    MyListP13<Object> encoded = list.encodeDirect();

    assertEquals(1, encoded.length());
    assertEquals("a", encoded.elementAt(1 + 0));
  }

  /** Tests encoding with numeric values. */
  @Test
  void testWithNumbers() {
    MyListP13<Integer> list = new MyListP13<>(1, 1, 1, 2, 3, 3, 4);
    MyListP13<Object> encoded = list.encodeDirect();
    System.out.println(encoded);
    assertEquals(4, encoded.length());
    assertEquals(new MyListP10.EncodedElement<>(3L, 1), encoded.elementAt(1 + 0));
    assertEquals(2, encoded.elementAt(1 + 1));
    assertEquals(new MyListP10.EncodedElement<>(2L, 3), encoded.elementAt(1 + 2));
    assertEquals(4, encoded.elementAt(1 + 3));
  }

  /** Tests encoding with alternating elements. */
  @Test
  void testAlternatingElements() {
    MyListP13<String> list = new MyListP13<>("a", "b", "a", "b", "a", "b");
    MyListP13<Object> encoded = list.encodeDirect();

    assertEquals(6, encoded.length());
    assertEquals("a", encoded.elementAt(1 + 0));
    assertEquals("b", encoded.elementAt(1 + 1));
    assertEquals("a", encoded.elementAt(1 + 2));
    assertEquals("b", encoded.elementAt(1 + 3));
    assertEquals("a", encoded.elementAt(1 + 4));
    assertEquals("b", encoded.elementAt(1 + 5));
  }

  /** Tests that the encode method works correctly with null elements. */
  @Test
  void testWithNulls() {
    MyListP13<String> list = new MyListP13<>(null, null, "a", "a", null);
    assertThrows(NullPointerException.class, list::encodeDirect);
  }

  /** Tests encoding and then decoding returns the original list. */
  @Test
  void testEncodeThenDecode() {
    MyListP13<String> original = new MyListP13<>("a", "a", "a", "b", "c", "c", "a");
    MyListP13<Object> encoded = original.encodeDirect();
    MyListP13<String> decoded = encoded.decode();
    assertEquals(original.length(), decoded.length());
    for (int i = 0; i < original.length(); i++) {
      assertEquals(original.elementAt(1 + i), decoded.elementAt(1 + i));
    }
  }
}
