package org.nintynine.problems;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TruthP48Test {
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testBasicOperations() {
        // Test AND
        List<String> vars = Arrays.asList("A", "B");
        TruthP48.table(vars, "A and B");
        String output = outContent.toString();
        assertTrue(output.matches("(?s).*\\bfalse\\s+false\\s+false\\b.*"));
        assertTrue(output.matches("(?s).*\\bfalse\\s+true\\s+false\\b.*"));
        assertTrue(output.matches("(?s).*\\btrue\\s+false\\s+false\\b.*"));
        assertTrue(output.matches("(?s).*\\btrue\\s+true\\s+true\\b.*"));
        
        // Clear output
        outContent.reset();
        
        // Test OR
        TruthP48.table(vars, "A or B");
        output = outContent.toString();
        assertTrue(output.matches("(?s).*\\bfalse\\s+false\\s+false\\b.*"));
        assertTrue(output.matches("(?s).*\\bfalse\\s+true\\s+true\\b.*"));
        assertTrue(output.matches("(?s).*\\btrue\\s+false\\s+true\\b.*"));
        assertTrue(output.matches("(?s).*\\btrue\\s+true\\s+true\\b.*"));
    }

    @Test
    void testNotOperation() {
        List<String> vars = List.of("A");
        TruthP48.table(vars, "not A");
        String output = outContent.toString();
        assertTrue(output.matches("(?s).*false\\s+true.*"));  // matches any number of spaces
        assertTrue(output.matches("(?s).*true\\s+false.*"));
    }

    @Test
    void testEquivalence() {
        List<String> vars = Arrays.asList("A", "B");
        TruthP48.table(vars, "A equ B");
        String output = outContent.toString();
        assertTrue(output.matches("(?s).*false(?s).*false(?s).*true.*"));
        assertTrue(output.matches("(?s).*false(?s).*true(?s).*false.*"));
        assertTrue(output.matches("(?s).*true(?s).*false(?s).*false.*"));
        assertTrue(output.matches("(?s).*true(?s).*true(?s).*true.*"));
    }

    @Test
    void testComplexExpression() {
        List<String> vars = Arrays.asList("A", "B", "C");
        TruthP48.table(vars, "(A and (B or C)) equ ((A and B) or (A and C))");
        String output = outContent.toString();
        // This is a tautology - each result line should end with "true"
        assertTrue(output.matches("(?s).*\\n[^\\n]+\\s+true\\s*\\n.*"));  // header and separator lines
        assertFalse(output.matches("(?s).*\\n[^\\n]+\\s+false\\s*\\n.*")); // no lines should end with false
    }

    @Test
    void testThreeVariables() {
        List<String> vars = Arrays.asList("A", "B", "C");
        TruthP48.table(vars, "A and (B or C)");
        String output = outContent.toString();
        
        // First verify the header format
        assertTrue(output.matches("(?s)A\\s+B\\s+C\\s+Result.*"));
        
        // Verify the number of rows (should be 8 for 3 variables, plus header and separator)
        assertEquals(10, output.trim().split("\n").length);
        
        // Check some specific combinations with proper spacing
        assertTrue(output.matches("(?s).*\\bfalse\\s+false\\s+false\\s+false\\b.*"));
        assertTrue(output.matches("(?s).*\\btrue\\s+true\\s+false\\s+true\\b.*"));
        assertTrue(output.matches("(?s).*\\btrue\\s+false\\s+true\\s+true\\b.*"));
        
        // Verify no consecutive variables are printed without space
        assertFalse(output.contains("falsefalse"));
        assertFalse(output.contains("truetrue"));
        assertFalse(output.contains("falsetrue"));
        assertFalse(output.contains("truefalse"));
    }

    @Test
    void testNestedExpressions() {
        List<String> vars = Arrays.asList("A", "B");
        TruthP48.table(vars, "not (A and (not B))");
        String output = outContent.toString();
        String[] lines = output.split("\n");
        // Skip header (first line) and separator (second line)
        String actualOutput = String.join("\n", Arrays.copyOfRange(lines, 2, lines.length));

        String expected = """
        false   false   true
        true    false   false
        false   true    true
        true    true    true""";
    
        assertEquals(expected.trim(), actualOutput.trim());
    }

    @Test
    void testSingleVariable() {
        List<String> vars = List.of("A");
        TruthP48.table(vars, "A");
        String output = outContent.toString();
        String[] lines = output.trim().split("\n");
        assertTrue(lines.length >= 3); // Header, separator, and at least two value lines
        assertTrue(output.matches("(?s).*A\\s+Result.*")); // Header
        assertTrue(output.matches("(?s).*false\\s+false.*")); // First case
        assertTrue(output.matches("(?s).*true\\s+true.*")); // Second case
    }

    @Test
    void testInvalidExpression() {
        List<String> vars = Arrays.asList("A", "B");
        assertThrows(IllegalArgumentException.class, () -> TruthP48.table(vars, "A invalid B"));
    }

    @Test
    void testMissingVariable() {
        List<String> vars = List.of("A");
        assertThrows(NullPointerException.class, () -> TruthP48.table(vars, "A and B"));
    }

    @Test
    void testHeaderFormat() {
        List<String> vars = Arrays.asList("A", "B");
        TruthP48.table(vars, "A and B");
        String output = outContent.toString();
        String[] lines = output.split("\n");
        
        // Check header format
        assertTrue(lines[0].contains("A"));
        assertTrue(lines[0].contains("B"));
        assertTrue(lines[0].contains("Result"));
        
        // Check the separator line
        assertTrue(lines[1].matches("^-+$"));
    }

    @Test
    void testDistributiveProperty() {
        List<String> vars = Arrays.asList("A", "B", "C");
        // Test distributive property: A and (B or C) = (A and B) or (A and C)
        TruthP48.table(vars, "(A and (B or C)) equ ((A and B) or (A and C))");
        String output = outContent.toString();
        String[] lines = output.trim().split("\n");
        
        // Skip header and separator lines
        for (int i = 2; i < lines.length; i++) {
            String[] columns = lines[i].trim().split("\\s+");
            String result = columns[columns.length - 1]; // Get last column
            assertEquals("true", result, "Expected true for all results in truth table");
        }
    }
}
