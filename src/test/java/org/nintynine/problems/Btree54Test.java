package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Btree54Test {

  @Test
  @DisplayName("Test single node expressions")
  void testSingleNode() {
    assertTrue(Btree54.isTree("a"));
    assertTrue(Btree54.isTree("x"));
    assertTrue(Btree54.isTree("(a nil nil)"));
  }

  @Test
  @DisplayName("Test complex valid expressions")
  void testValidComplexExpressions() {
    assertTrue(Btree54.isTree("(a (b nil nil) nil)"));
    assertTrue(Btree54.isTree("(a nil (b nil nil))"));
    assertTrue(Btree54.isTree("(a (b nil nil) (c nil nil))"));
    assertTrue(Btree54.isTree("(a (b (c nil nil) nil) nil)"));
  }

  @ParameterizedTest
  @DisplayName("Test invalid expressions")
  @ValueSource(
      strings = {
        "",
        "nil",
        "()",
        "(a)",
        "(a nil)",
        "(a b c)",
        "(a (b nil nil)",
        "a nil nil",
        "(a (nil nil) nil)",
        "(a (b) nil)"
      })
  void testInvalidExpressions(String expression) {
    assertFalse(Btree54.isTree(expression));
  }

  @Test
  @DisplayName("Test tree parsing and reconstruction")
  void testTreeParsing() {
    String expr = "(a (b nil nil) (c nil nil))";
    Btree54.Btree54Node tree = Btree54.parseTree(expr);
    assertEquals(expr, tree.toString());
  }

  @Test
  @DisplayName("Test null and empty inputs")
  void testNullAndEmpty() {
    assertFalse(Btree54.isTree(null));
    assertFalse(Btree54.isTree(""));
    assertFalse(Btree54.isTree("  "));
  }

  @Test
  @DisplayName("Test node creation and equality")
  void testNodeCreation() {
    Btree54.Btree54Node leaf1 = new Btree54.Btree54Node("x");
    Btree54.Btree54Node leaf2 = new Btree54.Btree54Node("x");
    Btree54.Btree54Node bTree54Node1 = new Btree54.Btree54Node("a", leaf1, null);
    Btree54.Btree54Node bTree54Node = new Btree54.Btree54Node("a", leaf2, null);

    assertEquals(leaf1, leaf2);
    assertEquals(bTree54Node1, bTree54Node);
    assertNotEquals(leaf1, bTree54Node1);
  }

  @Test
  @DisplayName("Test node construction with null value")
  void testNodeNullValue() {
    assertThrows(NullPointerException.class, () -> new Btree54.Btree54Node(null));
  }

  @Test
  @DisplayName("Test complex tree structure")
  void testComplexTree() {
    String complexExpr = "(root (left (ll nil nil) (lr nil nil)) (right nil (rr nil nil)))";
    assertTrue(Btree54.isTree(complexExpr));

    Btree54.Btree54Node tree = Btree54.parseTree(complexExpr);
    assertEquals(complexExpr, tree.toString());
  }

  @Test
  @DisplayName("Test invalid characters in values")
  void testInvalidCharacters() {
    assertFalse(Btree54.isTree("(a(b nil nil) nil)"));
    assertFalse(Btree54.isTree("(a) (b nil nil) nil)"));
    assertFalse(Btree54.isTree("(a,b nil nil)"));
  }
}
