package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyListP05Test {

  @Test
  void testReverseEmptyList() {
    MyListP05<String> list = new MyListP05<>();
    assertArrayEquals(new String[] {}, list.reverse().items);
  }

  @Test
  void testReverseSingleElement() {
    MyListP05<Integer> list = new MyListP05<>(1);
    assertArrayEquals(new Integer[] {1}, list.reverse().items);
  }

  @Test
  void testReverseMultipleElements() {
    MyListP05<String> list = new MyListP05<>("a", "b", "c", "d", "e");
    assertArrayEquals(new String[] {"e", "d", "c", "b", "a"}, list.reverse().items);
  }

  @Test
  void testReverseWithNullValues() {
    MyListP05<String> list = new MyListP05<>("a", null, "c");
    assertArrayEquals(new String[] {"c", null, "a"}, list.reverse().items);
  }

  @Test
  void testReverseWithDifferentTypes() {
    MyListP05<Integer> intList = new MyListP05<>(1, 2, 3);
    assertArrayEquals(new Integer[] {3, 2, 1}, intList.reverse().items);

    MyListP05<Double> doubleList = new MyListP05<>(1.0, 2.0, 3.0);
    assertArrayEquals(new Double[] {3.0, 2.0, 1.0}, doubleList.reverse().items);

    MyListP05<Boolean> boolList = new MyListP05<>(true, false, true);
    assertArrayEquals(new Boolean[] {true, false, true}, boolList.reverse().items);
  }

  @Test
  void testReverseTwice() {
    MyListP05<String> list = new MyListP05<>("a", "b", "c");
    assertArrayEquals(new String[] {"c", "b", "a"}, list.reverse().items);
    assertArrayEquals(new String[] {"a", "b", "c"}, list.reverse().reverse().items);
  }

  @Test
  void testReversePalindrome() {
    MyListP05<String> list = new MyListP05<>("a", "b", "a");
    assertArrayEquals(new String[] {"a", "b", "a"}, list.reverse().items);
  }
}
