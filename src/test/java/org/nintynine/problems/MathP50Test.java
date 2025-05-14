package org.nintynine.problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MathP50Test {

    @Test
    @DisplayName("Test example from problem description")
    void testExampleCase() {
        List<MathP50.FrequencyEntry> frequencies = List.of(
                new MathP50.FrequencyEntry("a", 45),
                new MathP50.FrequencyEntry("b", 13),
                new MathP50.FrequencyEntry("c", 12),
                new MathP50.FrequencyEntry("d", 16),
                new MathP50.FrequencyEntry("e", 9),
                new MathP50.FrequencyEntry("f", 5)
        );

        List<MathP50.HuffmanCode> codes = MathP50.huffman(frequencies);

        // Verify properties of the generated codes
        assertValidHuffmanCodes(codes, frequencies);
    }

    @Test
    @DisplayName("Test single symbol case")
    void testSingleSymbol() {
        List<MathP50.FrequencyEntry> frequencies = List.of(
                new MathP50.FrequencyEntry("a", 1)
        );

        List<MathP50.HuffmanCode> codes = MathP50.huffman(frequencies);
        assertEquals(1, codes.size());
        assertEquals("0", codes.getFirst().code());
    }

    @Test
    @DisplayName("Test two symbols case")
    void testTwoSymbols() {
        List<MathP50.FrequencyEntry> frequencies = List.of(
                new MathP50.FrequencyEntry("a", 1),
                new MathP50.FrequencyEntry("b", 1)
        );

        List<MathP50.HuffmanCode> codes = MathP50.huffman(frequencies);
        assertEquals(2, codes.size());
        assertNotEquals(codes.get(0).code(), codes.get(1).code());
    }

    @Test
    @DisplayName("Test invalid inputs")
    void testInvalidInputs() {
        List<MathP50.FrequencyEntry> emptyFreq = List.of();
        assertThrows(IllegalArgumentException.class,
                () -> MathP50.huffman(emptyFreq));

        assertThrows(IllegalArgumentException.class,
                () -> MathP50.huffman(null));

        assertThrows(IllegalArgumentException.class,
                () -> new MathP50.FrequencyEntry("a", -1));

        assertThrows(NullPointerException.class,
                () -> new MathP50.FrequencyEntry(null, 1));
    }

    @Test
    @DisplayName("Test encoding and decoding")
    void testEncodingDecoding() {
        List<MathP50.FrequencyEntry> frequencies = List.of(
                new MathP50.FrequencyEntry("a", 45),
                new MathP50.FrequencyEntry("b", 13),
                new MathP50.FrequencyEntry("c", 12)
        );

        List<MathP50.HuffmanCode> codes = MathP50.huffman(frequencies);
        MathP50.HuffmanDecoder decoder = MathP50.createDecoder(codes);

        // Create a mapping for encoding
        Map<String, String> encodeMap = new HashMap<>();
        codes.forEach(code -> encodeMap.put(code.symbol(), code.code()));

        // Test message
        String message = "abcabc";
        StringBuilder encoded = new StringBuilder();
        for (char c : message.toCharArray()) {
            encoded.append(encodeMap.get(String.valueOf(c)));
        }

        assertEquals(message, decoder.decode(encoded.toString()));
    }

    @Test
    @DisplayName("Test decoder with invalid input")
    void testDecoderInvalidInput() {
        List<MathP50.HuffmanCode> codes = List.of(
                new MathP50.HuffmanCode("a", "0"),
                new MathP50.HuffmanCode("b", "1")
        );

        MathP50.HuffmanDecoder decoder = MathP50.createDecoder(codes);

        assertThrows(IllegalArgumentException.class,
                () -> decoder.decode("2"));
        assertThrows(IllegalArgumentException.class,
                () -> decoder.decode("01x"));
        assertThrows(IllegalArgumentException.class,
                () -> decoder.decode("ena")); // Invalid code sequence
    }

    private void assertValidHuffmanCodes(
            List<MathP50.HuffmanCode> codes,
            List<MathP50.FrequencyEntry> frequencies) {
        // Check that we have a code for each symbol
        assertEquals(frequencies.size(), codes.size());

        // Check that codes are unique
        Set<String> uniqueCodes = new HashSet<>();
        for (MathP50.HuffmanCode code : codes) {
            assertTrue(uniqueCodes.add(code.code()),
                    "Codes must be unique");
        }

        // Check prefix property
        for (MathP50.HuffmanCode code1 : codes) {
            for (MathP50.HuffmanCode code2 : codes) {
                if (code1 != code2) {
                    assertFalse(code1.code().startsWith(code2.code()),
                            "No code should be a prefix of another code");
                }
            }
        }

        // Verify that more frequent symbols have shorter codes
        for (MathP50.FrequencyEntry f1 : frequencies) {
            for (MathP50.FrequencyEntry f2 : frequencies) {
                if (f1.frequency() > f2.frequency()) {
                    String code1 = getCodeForSymbol(codes, f1.symbol());
                    String code2 = getCodeForSymbol(codes, f2.symbol());
                    assertTrue(code1.length() <= code2.length(),
                            "More frequent symbols should have shorter or equal length codes");
                }
            }
        }
    }

    private String getCodeForSymbol(List<MathP50.HuffmanCode> codes, String symbol) {
        return codes.stream()
                .filter(c -> c.symbol().equals(symbol))
                .findFirst()
                .map(MathP50.HuffmanCode::code)
                .orElseThrow();
    }
}
