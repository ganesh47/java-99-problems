package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    /**
     * Tests the main method of the Main class.
     * The test captures the standard output and verifies that the expected output is printed correctly.
     */
    @Test
    void testMainMethodOutput() {
        // Arrange: Redirect system output to capture printed statements
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act: Invoke the main method
        Main.main(new String[]{});

        // Assert: Verify the expected output
        String expectedOutput = "Hello and welcome!" + System.lineSeparator() +
                "i = 1" + System.lineSeparator() +
                "i = 2" + System.lineSeparator() +
                "i = 3" + System.lineSeparator() +
                "i = 4" + System.lineSeparator() +
                "i = 5" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());

        // Reset system output
        System.setOut(System.out);
    }
}