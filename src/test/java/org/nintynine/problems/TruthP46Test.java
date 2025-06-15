package org.nintynine.problems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TruthP46Test {

  @Test
  @DisplayName("Test example: (and A (or A B))")
  void testExample() {
    List<TruthP46.TruthTableRow> table = TruthP46.table("(and A (or A B))");

    assertFalse(table.get(0).result()); // F F -> F
    assertFalse(table.get(1).result()); // F T -> F
    assertTrue(table.get(2).result()); // T F -> T
    assertTrue(table.get(3).result()); // T T -> T

    // Print the table
    System.out.println("Truth table for (and A (or A B)):");
    System.out.println(TruthP46.formatTruthTable(table));
  }

  @Test
  @DisplayName("Test all logical operators")
  void testAllOperators() {
    // Test AND
    verifyOperation("(and A B)", new boolean[] {false, false, false, true});

    // Test OR
    verifyOperation("(or A B)", new boolean[] {false, true, true, true});

    // Test NAND
    verifyOperation("(nand A B)", new boolean[] {true, true, true, false});

    // Test NOR
    verifyOperation("(nor A B)", new boolean[] {true, false, false, false});

    // Test XOR
    verifyOperation("(xor A B)", new boolean[] {false, true, true, false});

    // Test IMPL
    verifyOperation("(impl A B)", new boolean[] {true, true, false, true});

    // Test EQU
    verifyOperation("(equ A B)", new boolean[] {true, false, false, true});
  }

  @Test
  @DisplayName("Test complex expressions")
  void testComplexExpressions() {
    // (A or B) and (A nand B)
    List<TruthP46.TruthTableRow> table = TruthP46.table("(and (or A B) (nand A B))");

    System.out.println("Truth table for (and (or A B) (nand A B)):");
    System.out.println(TruthP46.formatTruthTable(table));

    // Verify results
    assertFalse(table.get(0).result()); // F F -> F
    assertTrue(table.get(1).result()); // F T -> T
    assertTrue(table.get(2).result()); // T F -> T
    assertFalse(table.get(3).result()); // T T -> F
  }

  @ParameterizedTest
  @DisplayName("Test invalid expressions")
  @ValueSource(
      strings = {
        "", // Empty
        "X", // Invalid variable
        "()", // Empty parentheses
        "(and)", // Missing operands
        "(and A)", // Missing second operand
        "(invalid A B)", // Invalid operator
        "(and A B C)", // Too many operands
        "(and A (or B))" // Incomplete nested expression
      })
  void testInvalidExpressions(String expression) {
    assertThrows(IllegalArgumentException.class, () -> TruthP46.table(expression));
  }

  @Test
  @DisplayName("Test nested expressions")
  void testNestedExpressions() {
    // ((A and B) or (A xor B))
    List<TruthP46.TruthTableRow> table = TruthP46.table("(or (and A B) (xor A B))");

    System.out.println("Truth table for (or (and A B) (xor A B)):");
    System.out.println(TruthP46.formatTruthTable(table));

    // Verify specific cases
    assertFalse(table.get(0).result()); // F F -> F
    assertTrue(table.get(1).result()); // F T -> T
    assertTrue(table.get(2).result()); // T F -> T
    assertTrue(table.get(3).result()); // T T -> T
  }

  private void verifyOperation(String expression, boolean[] expectedResults) {
    List<TruthP46.TruthTableRow> table = TruthP46.table(expression);
    assertEquals(4, table.size());

    for (int i = 0; i < 4; i++) {
      assertEquals(
          expectedResults[i], table.get(i).result(), "Mismatch at row " + i + " for " + expression);
    }

    // Print the table
    System.out.println("Truth table for " + expression + ":");
    System.out.println(TruthP46.formatTruthTable(table));
  }
}
