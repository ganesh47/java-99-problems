package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Additional tests to improve coverage of small utility classes. */
class AdditionalCoverageTest {

  @Test
  @DisplayName("Utility classes private constructors coverage")
  void testPrivateConstructors() throws Exception {
    assertPrivateConstructor(Btree54.class);
    assertPrivateConstructor(BtreeP57.class);
    assertPrivateConstructor(BtreeP58.class);
    assertPrivateConstructor(BtreeP59.class);
    assertPrivateConstructor(BtreeP60.class);
    assertPrivateConstructor(BtreeP61.class);
    assertPrivateConstructor(BtreeP61A.class);
    assertPrivateConstructor(BtreeP62.class);
    assertPrivateConstructor(BtreeP62B.class);
    assertPrivateConstructor(BtreeP63.class);
    assertPrivateConstructor(BtreeP64.class);
    assertPrivateConstructor(BtreeP65.class);
    assertPrivateConstructor(BtreeP66.class);
    assertPrivateConstructor(BtreeP67.class);
    assertPrivateConstructor(BtreeP68.class);
    assertPrivateConstructor(BtreeP69.class);
    assertPrivateConstructor(MathP31.class);
    assertPrivateConstructor(MathP32.class);
    assertPrivateConstructor(MathP33.class);
    assertPrivateConstructor(MathP34.class);
    assertPrivateConstructor(MathP35.class);
    assertPrivateConstructor(MathP36.class);
    assertPrivateConstructor(MathP37.class);
    assertPrivateConstructor(MathP38.class);
    assertPrivateConstructor(MathP39.class);
    assertPrivateConstructor(MathP40.class);
    assertPrivateConstructor(MathP41.class);
    assertPrivateConstructor(MathP43.class);
    assertPrivateConstructor(MathP50.class);
    assertPrivateConstructor(LogicalExpression.class);
    assertPrivateConstructor(TruthP46.class);
    assertPrivateConstructor(TruthP47.class);
    assertPrivateConstructor(TruthP48.class);
    assertPrivateConstructor(TruthP55.class);
  }

  private void assertPrivateConstructor(Class<?> clazz) throws Exception {
    Constructor<?> constructor = clazz.getDeclaredConstructor();
    assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()),
        "Constructor of " + clazz.getName() + " should be private");
    constructor.setAccessible(true);
    constructor.newInstance();
  }

  @Test
  @DisplayName("BtreeP61.Node nodeCount and other methods")
  void testNodeMethods() {
    BtreeP61.Node<String> leaf = BtreeP61.Node.leaf("a");
    assertEquals(1, leaf.nodeCount());
    assertTrue(leaf.isHeightBalanced());
    assertEquals("a", leaf.toString());

    BtreeP61.Node<String> root = BtreeP61.Node.of("r", leaf, null);
    assertEquals(2, root.nodeCount());
    assertEquals(2, root.height());
    assertTrue(root.isHeightBalanced());
    assertEquals("r(a,)", root.toString());
  }

  @Test
  @DisplayName("HuffmanCode and FrequencyEntry coverage")
  void testMathP50Structures() {
    MathP50.HuffmanCode code = new MathP50.HuffmanCode("a", "0");
    assertEquals("HuffmanCode[symbol=a, code=0]", code.toString());

    MathP50.FrequencyEntry entry = new MathP50.FrequencyEntry("a", 10);
    assertTrue(entry.toString().contains("symbol=a"));
    assertTrue(entry.toString().contains("frequency=10"));
  }

  @Test
  @DisplayName("LogicalOp toString and states")
  void testLogicalOp() {
    assertTrue(LogicalExpression.LogicalOp.AND.toString().contains("AND"));
    assertThrows(IllegalStateException.class, () -> LogicalExpression.LogicalOp.NOT.apply(true, true));
    assertThrows(IllegalStateException.class, () -> LogicalExpression.LogicalOp.AND.apply(true));
  }
}
