package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test class for MyListP18's slice functionality. */
@SuppressWarnings("PointlessArithmeticExpression")
class MyListP18Test {

  private MyListP18<String> list;

  @BeforeEach
  void setUp() {
    list = new MyListP18<>("a", "b", "c", "d", "e", "f", "g");
  }

  /** Tests basic slice extraction. */
  @Test
  void testBasicSlice() {
    MyListP18<String> sliced = list.slice(3, 5);

    assertEquals(3, sliced.length());
    assertEquals("c", sliced.elementAt(1 + 0));
    assertEquals("d", sliced.elementAt(1 + 1));
    assertEquals("e", sliced.elementAt(1 + 2));
  }

  /** Tests slice of entire list. */
  @Test
  void testFullListSlice() {
    MyListP18<String> sliced = list.slice(1, 7);

    assertEquals(7, sliced.length());
    assertEquals("a", sliced.elementAt(1 + 0));
    assertEquals("g", sliced.elementAt(1 + 6));
  }

  /** Tests single element slice. */
  @Test
  void testSingleElementSlice() {
    MyListP18<String> sliced = list.slice(3, 3);

    assertEquals(1, sliced.length());
    assertEquals("c", sliced.elementAt(1 + 0));
  }

  /** Tests various invalid slice parameters. */
  @Test
  void testInvalidSliceParameters() {
    assertThrows(IllegalArgumentException.class, () -> list.slice(0, 3));
    assertThrows(IllegalArgumentException.class, () -> list.slice(-1, 3));
    assertThrows(IllegalArgumentException.class, () -> list.slice(1, 8));
    assertThrows(IllegalArgumentException.class, () -> list.slice(4, 2));
  }

  /** Tests slice with null values. */
  @Test
  void testSliceWithNulls() {
    MyListP18<String> listWithNulls = new MyListP18<>("a", null, "c", null, "e");
    MyListP18<String> sliced = listWithNulls.slice(2, 4);

    assertEquals(3, sliced.length());
    assertThrows(NullPointerException.class, () -> sliced.elementAt(1 + 0));
    assertEquals("c", sliced.elementAt(1 + 1));
    assertThrows(NullPointerException.class, () -> sliced.elementAt(1 + 2));
  }

  /** Tests that slice preserves object references. */
  @Test
  void testObjectReferences() {
    StringBuilder sb1 = new StringBuilder("test1");
    StringBuilder sb2 = new StringBuilder("test2");
    StringBuilder sb3 = new StringBuilder("test3");

    MyListP18<StringBuilder> builderList = new MyListP18<>(sb1, sb2, sb3);
    MyListP18<StringBuilder> sliced = builderList.slice(1, 2);

    assertEquals(2, sliced.length());
    assertSame(sb1, sliced.elementAt(1 + 0));
    assertSame(sb2, sliced.elementAt(1 + 1));
  }

  /** Tests slicing with different types. */
  @Test
  void testDifferentTypes() {
    // Test with integers
    MyListP18<Integer> intList = new MyListP18<>(1, 2, 3, 4, 5);
    MyListP18<Integer> intSliced = intList.slice(2, 4);

    assertEquals(3, intSliced.length());
    assertEquals(Integer.valueOf(2), intSliced.elementAt(1 + 0));
    assertEquals(Integer.valueOf(4), intSliced.elementAt(1 + 2));

    // Test with doubles
    MyListP18<Double> doubleList = new MyListP18<>(1.0, 2.0, 3.0, 4.0);
    MyListP18<Double> doubleSliced = doubleList.slice(2, 3);

    assertEquals(2, doubleSliced.length());
    assertEquals(2.0, doubleSliced.elementAt(1 + 0), 0.001);
    assertEquals(3.0, doubleSliced.elementAt(1 + 1), 0.001);
  }

  /** Tests slice at list boundaries. */
  @Test
  void testBoundarySlices() {
    // Slice from start
    MyListP18<String> startSlice = list.slice(1, 3);
    assertEquals(3, startSlice.length());
    assertEquals("a", startSlice.elementAt(1 + 0));
    assertEquals("c", startSlice.elementAt(1 + 2));

    // Slice to end
    MyListP18<String> endSlice = list.slice(5, 7);
    assertEquals(3, endSlice.length());
    assertEquals("e", endSlice.elementAt(1 + 0));
    assertEquals("g", endSlice.elementAt(1 + 2));
  }

  /** Tests slice with empty list. */
  @Test
  void testEmptyList() {
    MyListP18<String> emptyList = new MyListP18<>();
    assertThrows(IllegalArgumentException.class, () -> emptyList.slice(1, 1));
  }

  /** Tests slice with consecutive indices. */
  @Test
  void testConsecutiveIndices() {
    for (int i = 1; i < list.length(); i++) {
      MyListP18<String> sliced = list.slice(i, i + 1);
      assertEquals(2, sliced.length());
      assertEquals(list.elementAt(1 + i - 1), sliced.elementAt(1 + 0));
      assertEquals(list.elementAt(1 + i), sliced.elementAt(1 + 1));
    }
  }

  /** Tests that slicing creates a new independent list. */
  @Test
  void testSliceIndependence() {
    StringBuilder[] builders = {
      new StringBuilder("1"), new StringBuilder("2"), new StringBuilder("3")
    };

    MyListP18<StringBuilder> originalList = new MyListP18<>(builders);
    MyListP18<StringBuilder> sliced = originalList.slice(1, 2);

    // Modify original StringBuilder objects
    builders[0].append("-modified");
    builders[1].append("-modified");

    // Changes should be reflected in both lists since they share references
    assertEquals("1-modified", sliced.elementAt(1 + 0).toString());
    assertEquals("2-modified", sliced.elementAt(1 + 1).toString());

    // But the lists themselves should be independent
    assertNotSame(originalList, sliced);
  }
}
