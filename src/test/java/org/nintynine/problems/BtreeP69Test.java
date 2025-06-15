package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BtreeP69Test {

  @Test
  void testDotstringExample() {
    String ds = "ABD..E..C.FG...";
    BtreeP69.Node tree = BtreeP69.tree(ds);
    assertEquals(ds, BtreeP69.dotstring(tree));
  }

  @Test
  void testSingleNode() {
    String ds = "A..";
    BtreeP69.Node tree = BtreeP69.tree(ds);
    assertEquals('A', tree.value);
    assertNull(tree.left);
    assertNull(tree.right);
    assertEquals(ds, BtreeP69.dotstring(tree));
  }

  @Test
  void testNullTree() {
    assertNull(BtreeP69.tree("."));
    assertEquals(".", BtreeP69.dotstring(null));
  }

  @Test
  void testInvalidDotstring() {
    assertThrows(IllegalArgumentException.class, () -> BtreeP69.tree("A."));
    assertThrows(IllegalArgumentException.class, () -> BtreeP69.tree("A..."));
  }

  @Test
  void testNullInput() {
    assertThrows(IllegalArgumentException.class, () -> BtreeP69.tree(null));
  }

  @Test
  void testIncompleteDotstring() {
    assertThrows(IllegalArgumentException.class, () -> BtreeP69.tree("A"));
  }

  @Test
  void testExtraCharacters() {
    assertThrows(IllegalArgumentException.class, () -> BtreeP69.tree("A..B"));
  }

  @Test
  void testToStringRepresentation() {
    BtreeP69.Node tree = BtreeP69.tree("ABD..E..C.FG...");
    assertEquals("A(B(D,E),C(NIL,F(G,NIL)))", tree.toString());
  }
}
