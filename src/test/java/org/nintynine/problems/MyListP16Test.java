package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Test class for MyListP16's element dropping functionality. */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP16Test {

  /** Tests basic dropping of elements. */
  @Test
  void testBasicDrop() {
    MyListP16<String> list = new MyListP16<>("a", "b", "c", "d", "e", "f");
    MyListP16<String> dropped = list.drop(3);

    assertEquals(4, dropped.length());
    assertEquals("a", dropped.elementAt(1 + 0));
    assertEquals("b", dropped.elementAt(1 + 1));
    assertEquals("d", dropped.elementAt(1 + 2));
    assertEquals("e", dropped.elementAt(1 + 3));
  }

  /** Tests dropping with n=1 (should result in empty list). */
  @Test
  void testDropEveryElement() {
    MyListP16<String> list = new MyListP16<>("a", "b", "c");
    MyListP16<String> dropped = list.drop(1);
    assertEquals(0, dropped.length());
  }

  /** Tests dropping with n greater than list length. */
  @Test
  void testDropGreaterThanLength() {
    MyListP16<String> list = new MyListP16<>("a", "b", "c");
    MyListP16<String> dropped = list.drop(4);

    assertEquals(3, dropped.length());
    assertEquals("a", dropped.elementAt(1 + 0));
    assertEquals("b", dropped.elementAt(1 + 1));
    assertEquals("c", dropped.elementAt(1 + 2));
  }

  /** Tests dropping from empty list. */
  @Test
  void testEmptyList() {
    MyListP16<String> list = new MyListP16<>();
    MyListP16<String> dropped = list.drop(2);
    assertEquals(0, dropped.length());
  }

  /** Tests dropping with invalid n. */
  @Test
  void testInvalidN() {
    MyListP16<String> list = new MyListP16<>("a", "b", "c");
    assertThrows(IllegalArgumentException.class, () -> list.drop(0));
    assertThrows(IllegalArgumentException.class, () -> list.drop(-1));
  }

  /** Tests dropping with null values in list. */
  @Test
  void testWithNulls() {
    MyListP16<String> list = new MyListP16<>("a", null, "b", null, "c");
    MyListP16<String> dropped = list.drop(2);

    assertEquals(3, dropped.length());
    assertEquals("a", dropped.elementAt(1 + 0));
    assertEquals("b", dropped.elementAt(1 + 1));
    assertEquals("c", dropped.elementAt(1 + 2));
  }

  /** Tests that object references are preserved. */
  @Test
  void testObjectReferences() {
    StringBuilder sb1 = new StringBuilder("test1");
    StringBuilder sb2 = new StringBuilder("test2");
    StringBuilder sb3 = new StringBuilder("test3");

    MyListP16<StringBuilder> list = new MyListP16<>(sb1, sb2, sb3);
    MyListP16<StringBuilder> dropped = list.drop(2);

    assertEquals(2, dropped.length());
    assertSame(sb1, dropped.elementAt(1 + 0));
    assertSame(sb3, dropped.elementAt(1 + 1));
  }

  /** Tests dropping with different types. */
  @Test
  void testDifferentTypes() {
    MyListP16<Integer> intList = new MyListP16<>(1, 2, 3, 4, 5);
    MyListP16<Integer> droppedInts = intList.drop(2);

    assertEquals(3, droppedInts.length());
    assertEquals(Integer.valueOf(1), droppedInts.elementAt(1 + 0));
    assertEquals(Integer.valueOf(3), droppedInts.elementAt(1 + 1));
    assertEquals(Integer.valueOf(5), droppedInts.elementAt(1 + 2));

    MyListP16<Double> doubleList = new MyListP16<>(1.0, 2.0, 3.0, 4.0);
    MyListP16<Double> droppedDoubles = doubleList.drop(3);

    assertEquals(3, droppedDoubles.length());
    assertEquals(1.0, droppedDoubles.elementAt(1 + 0), 0.001);
    assertEquals(2.0, droppedDoubles.elementAt(1 + 1), 0.001);
    assertEquals(4.0, droppedDoubles.elementAt(1 + 2), 0.001);
  }

  /** Tests dropping with single element list. */
  @Test
  void testSingleElement() {
    MyListP16<String> list = new MyListP16<>("a");
    MyListP16<String> dropped = list.drop(2);

    assertEquals(1, dropped.length());
    assertEquals("a", dropped.elementAt(1 + 0));
  }

  /** Tests dropping with a large n value. */
  @Test
  void testLargeN() {
    MyListP16<String> list = new MyListP16<>("a", "b", "c");
    MyListP16<String> dropped = list.drop(1000);

    assertEquals(3, dropped.length());
    assertEquals("a", dropped.elementAt(1 + 0));
    assertEquals("b", dropped.elementAt(1 + 1));
    assertEquals("c", dropped.elementAt(1 + 2));
  }
}
