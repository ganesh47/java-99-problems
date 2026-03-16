package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BtreeP69Test {

  @Test
  void testDotstringExample() {
    String ds = "ABD..E..C.FG...";
    BtreeP61.Node<Character> tree = BtreeP69.fromDotstring(ds);
    assertEquals(ds, BtreeP69.toDotstring(tree));
  }

  @Test
  void testSingleNode() {
    String ds = "A..";
    BtreeP61.Node<Character> tree = BtreeP69.fromDotstring(ds);
    assertEquals('A', tree.getValue());
    assertNull(tree.getLeft());
    assertNull(tree.getRight());
    assertEquals(ds, BtreeP69.toDotstring(tree));
  }

  @Test
  void testNullTree() {
    assertNull(BtreeP69.fromDotstring("."));
    assertEquals(".", BtreeP69.toDotstring(null));
  }

  @Test
  void testToStringRepresentation() {
    BtreeP61.Node<Character> tree = BtreeP69.fromDotstring("ABD..E..C.FG...");
    assertEquals("A(B(D,E),C(,F(G,)))", tree.toString());
  }
}
