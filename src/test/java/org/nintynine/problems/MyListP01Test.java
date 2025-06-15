package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyListP01Test {

  @Test
  void testLastWithIntegers() {
    MyList<Integer> list = new MyList<>(1, 2, 3, 4, 5);
    assertEquals(5, list.last());

    MyList<Integer> singleElement = new MyList<>(42);
    assertEquals(42, singleElement.last());
  }

  @Test
  void testLastWithStrings() {
    MyList<String> list = new MyList<>("apple", "banana", "cherry");
    assertEquals("cherry", list.last());

    MyList<String> singleElement = new MyList<>("solo");
    assertEquals("solo", singleElement.last());
  }

  @Test
  void testLastWithCustomObjects() {
    TestObject obj1 = new TestObject("first");
    TestObject obj2 = new TestObject("second");
    TestObject obj3 = new TestObject("third");

    MyList<TestObject> list = new MyList<>(obj1, obj2, obj3);
    assertEquals(obj3, list.last());
  }

  @Test
  void testLastWithNull() {
    MyList<String> list = new MyList<>("first", null, "last");
    assertEquals("last", list.last());
    MyList<String> listEndingWithNull = new MyList<>("first", "second", null);
    assertThrows(NullPointerException.class, listEndingWithNull::last);
  }

  @Test
  void testEmptyListThrowsException() {
    MyList<String> emptyList = new MyList<>();
    IllegalStateException exception = assertThrows(IllegalStateException.class, emptyList::last);
    assertEquals("Empty list has no last element", exception.getMessage());
  }

  @Test
  void testToString() {
    MyList<Integer> numbers = new MyList<>(1, 2, 3);
    assertEquals("(1 2 3)", numbers.toString());

    MyList<String> strings = new MyList<>("a", "b", "c");
    assertEquals("(a b c)", strings.toString());

    MyList<String> empty = new MyList<>();
    assertEquals("()", empty.toString());
  }

  // Helper class for testing with custom objects
  private static class TestObject {
    private final String value;

    TestObject(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
}
