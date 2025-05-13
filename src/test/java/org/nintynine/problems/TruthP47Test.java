package org.nintynine.problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TruthP47Test {

    @Test
    @DisplayName("Test example: A and (A or (not B))")
    void testExample() {
        List<TruthP47.TruthTableRow> table =
                TruthP47.table("(A and (A or (not B)))");

        System.out.println("Truth table for (A and (A or (not B))):");
        System.out.println(TruthP47.formatTruthTable(table));

        assertFalse(table.get(0).result()); // F F -> F
        assertFalse(table.get(1).result()); // F T -> F
        assertTrue(table.get(2).result());  // T F -> T
        assertTrue(table.get(3).result());  // T T -> T
    }

    @Test
    @DisplayName("Test all binary operators")
    void testAllBinaryOperators() {
        // Test AND
        verifyOperation("(A and B)",
                new boolean[]{false, false, false, true});

        // Test OR
        verifyOperation("(A or B)",
                new boolean[]{false, true, true, true});

        // Test NAND
        verifyOperation("(A nand B)",
                new boolean[]{true, true, true, false});

        // Test NOR
        verifyOperation("(A nor B)",
                new boolean[]{true, false, false, false});

        // Test XOR
        verifyOperation("(A xor B)",
                new boolean[]{false, true, true, false});

        // Test IMPL
        verifyOperation("(A impl B)",
                new boolean[]{true, true, false, true});

        // Test EQU
        verifyOperation("(A equ B)",
                new boolean[]{true, false, false, true});
    }

    @Test
    @DisplayName("Test NOT operator")
    void testNotOperator() {
        // Test simple NOT
        verifyOperation("(not A)",
                new boolean[]{true, true, false, false});

        // Test NOT with AND
        verifyOperation("(not (A and B))",
                new boolean[]{true, true, true, false});

        // Test nested NOT
        verifyOperation("(not (not (A and B)))",
                new boolean[]{false, false, false, true});
    }

    @Test
    @DisplayName("Test complex expressions")
    void testComplexExpressions() {
        // Test expression with multiple operators
        List<TruthP47.TruthTableRow> table =
                TruthP47.table("((A and B) or (not (A and B)))");

        System.out.println("Truth table for ((A and B) or (not (A and B))):");
        System.out.println(TruthP47.formatTruthTable(table));

        // This expression is a tautology (always true)
        assertTrue(table.stream().allMatch(TruthP47.TruthTableRow::result));
    }

    @ParameterizedTest
    @DisplayName("Test invalid expressions")
    @ValueSource(strings = {
            "",                         // Empty
            "X",                        // Invalid variable
            "()",                       // Empty parentheses
            "(A and)",                 // Missing operand
            "(and A B)",               // Prefix notation (invalid)
            "(A invalid B)",           // Invalid operator
            "(A and B C)",             // Too many operands
            "(A and (B))",             // Malformed expression
            "A and B"                  // Missing parentheses
    })
    void testInvalidExpressions(String expression) {
        assertThrows(IllegalArgumentException.class,
                () -> TruthP47.table(expression));
    }

    @Test
    @DisplayName("Test deeply nested expressions")
    void testDeeplyNestedExpressions() {
        String expression = "(A and (not (B or (not (A and B)))))";
        List<TruthP47.TruthTableRow> table = TruthP47.table(expression);

        System.out.println("Truth table for " + expression + ":");
        System.out.println(TruthP47.formatTruthTable(table));

        // Verify specific cases
        assertFalse(table.get(0).result()); // F F -> F
        assertFalse(table.get(1).result()); // F T -> F
        assertFalse(table.get(2).result()); // T F -> F
        assertFalse(table.get(3).result());  // T T -> T
    }

    private void verifyOperation(String expression, boolean[] expectedResults) {
        List<TruthP47.TruthTableRow> table = TruthP47.table(expression);
        assertEquals(4, table.size());

        for (int i = 0; i < 4; i++) {
            assertEquals(expectedResults[i], table.get(i).result(),
                    "Mismatch at row " + i + " for " + expression);
        }

        System.out.println("Truth table for " + expression + ":");
        System.out.println(TruthP47.formatTruthTable(table));
    }
}
