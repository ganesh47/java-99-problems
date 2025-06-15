package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class MyListP02Test {

  @Test
  void testLastButOneWithMultipleElements() {
    MyListP02<Integer> list = new MyListP02<>(1, 2, 3, 4, 5);
    assertEquals(4, list.lastButOne());
  }

  @Test
  void testLastButOneWithTwoElements() {
    MyListP02<String> list = new MyListP02<>("first", "second");
    assertEquals("first", list.lastButOne());
  }

  @Test
  void testLastButOneWithEmptyList() {
    MyListP02<Integer> list = new MyListP02<>();
    assertThrows(NoSuchElementException.class, list::lastButOne);
  }

  @Test
  void testLastButOneWithSingleElement() {
    MyListP02<String> list = new MyListP02<>("alone");
    assertThrows(NoSuchElementException.class, list::lastButOne);
  }

  @Test
  void testLastButOneWithNullValues() {
    MyListP02<String> list = new MyListP02<>("first", null, "last");
    assertThrows(NoSuchElementException.class, list::lastButOne);
  }

  @Test
  void testLastButOneWithCustomObjects() {
    Point p1 = new Point(1, 1);
    Point p2 = new Point(2, 2);
    Point p3 = new Point(3, 3);

    MyListP02<Point> list = new MyListP02<>(p1, p2, p3);
    assertEquals(p2, list.lastButOne());
  }

  @Test
  void testLastButOneWithBooleans() {
    MyListP02<Boolean> list = new MyListP02<>(true, false, true);
    assertEquals(false, list.lastButOne());
  }

  private record Point(int x, int y) {}
}
