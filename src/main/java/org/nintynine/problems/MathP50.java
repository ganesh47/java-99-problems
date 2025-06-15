package org.nintynine.problems;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * P50: Huffman coding implementation Creates Huffman codes for symbols based on their frequencies.
 */
public class MathP50 {
  private MathP50() {} // Prevent instantiation

  /** Represents a node in the Huffman tree */
  private static class HuffmanNode implements Comparable<HuffmanNode> {
    final String symbol;
    final int frequency;
    final HuffmanNode left;
    final HuffmanNode right;

    // Leaf node constructor
    HuffmanNode(String symbol, int frequency) {
      this.symbol = symbol;
      this.frequency = frequency;
      this.left = null;
      this.right = null;
    }

    // Internal node constructor
    HuffmanNode(HuffmanNode left, HuffmanNode right) {
      this.symbol = null;
      this.frequency = left.frequency + right.frequency;
      this.left = left;
      this.right = right;
    }

    boolean isLeaf() {
      return left == null && right == null;
    }

    @Override
    public int compareTo(HuffmanNode other) {
      int freqCompare = Integer.compare(this.frequency, other.frequency);
      if (freqCompare != 0) return freqCompare;
      // Break ties consistently for testing
      if (this.symbol == null || other.symbol == null) return 0;
      return this.symbol.compareTo(other.symbol);
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      HuffmanNode that = (HuffmanNode) o;
      return frequency == that.frequency
          && Objects.equals(symbol, that.symbol)
          && Objects.equals(left, that.left)
          && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
      return Objects.hash(symbol, frequency, left, right);
    }
  }

  /**
   * Represents a frequency table entry
   *
   * @param symbol The symbol
   * @param frequency Its frequency of occurrence
   */
  public record FrequencyEntry(String symbol, int frequency) {
    public FrequencyEntry {
      Objects.requireNonNull(symbol, "Symbol cannot be null");
      if (frequency < 0) {
        throw new IllegalArgumentException("Frequency cannot be negative");
      }
    }
  }

  /**
   * Represents a Huffman code table entry
   *
   * @param symbol The symbol
   * @param code It's Huffman code
   */
  public record HuffmanCode(String symbol, String code) {
    public HuffmanCode {
      Objects.requireNonNull(symbol, "Symbol cannot be null");
      Objects.requireNonNull(code, "Code cannot be null");
      if (!code.matches("[01]*")) {
        throw new IllegalArgumentException("Code must contain only 0s and 1s");
      }
    }
  }

  /**
   * Generates Huffman codes for the given frequency table.
   *
   * @param frequencies List of symbol-frequency pairs
   * @return List of symbol-code pairs, sorted by symbol
   * @throws IllegalArgumentException if a frequency list is empty or contains invalid entries
   */
  public static List<HuffmanCode> huffman(List<FrequencyEntry> frequencies) {
    if (frequencies == null || frequencies.isEmpty()) {
      throw new IllegalArgumentException("Frequency table cannot be empty");
    }

    // Handle a special case of a single symbol
    if (frequencies.size() == 1) {
      return List.of(new HuffmanCode(frequencies.getFirst().symbol(), "0"));
    }

    // Build a Huffman tree
    HuffmanNode root = buildHuffmanTree(frequencies);

    // Generate codes by traversing the tree
    Map<String, String> codes = new HashMap<>();
    generateCodes(root, "", codes);

    // Create and sort a result
    return frequencies.stream()
        .map(f -> new HuffmanCode(f.symbol(), codes.get(f.symbol())))
        .sorted(Comparator.comparing(HuffmanCode::symbol))
        .toList();
  }

  /** Builds a Huffman tree from the frequency table. */
  private static HuffmanNode buildHuffmanTree(List<FrequencyEntry> frequencies) {
    PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

    // Create leaf nodes
    for (FrequencyEntry entry : frequencies) {
      queue.offer(new HuffmanNode(entry.symbol(), entry.frequency()));
    }

    // Build a tree by combining nodes
    while (queue.size() > 1) {
      HuffmanNode left = queue.poll();
      HuffmanNode right = queue.poll();
      assert right != null;
      queue.offer(new HuffmanNode(left, right));
    }

    return queue.poll();
  }

  /**
   * Generates Huffman codes by traversing the tree. Uses '0' for left branches and '1' for right
   * branches.
   */
  private static void generateCodes(HuffmanNode node, String code, Map<String, String> codes) {
    if (node == null) return;

    if (node.isLeaf()) {
      codes.put(node.symbol, code);
      return;
    }

    generateCodes(node.left, code + "0", codes);
    generateCodes(node.right, code + "1", codes);
  }

  /**
   * Creates a decoder for the given Huffman codes.
   *
   * @param codes The Huffman code table
   * @return A decoder function that takes a binary string and returns the decoded message
   */
  public static HuffmanDecoder createDecoder(List<HuffmanCode> codes) {
    return new HuffmanDecoder(codes);
  }

  /** Decoder class for Huffman codes */
  public static class HuffmanDecoder {
    private final Node root = new Node();

    private static class Node {
      String symbol;
      final Node[] children = new Node[2];
    }

    private HuffmanDecoder(List<HuffmanCode> codes) {
      for (HuffmanCode code : codes) {
        insert(code.symbol(), code.code());
      }
    }

    private void insert(String symbol, String code) {
      Node current = root;
      for (char bit : code.toCharArray()) {
        int idx = bit - '0';
        if (current.children[idx] == null) {
          current.children[idx] = new Node();
        }
        current = current.children[idx];
      }
      current.symbol = symbol;
    }

    /**
     * Decodes a binary string using the Huffman codes.
     *
     * @param encoded The binary string to decode
     * @return The decoded message
     * @throws IllegalArgumentException if the input is invalid
     */
    public String decode(String encoded) {
      if (encoded == null || !encoded.matches("[01]*")) {
        throw new IllegalArgumentException("Invalid encoded string");
      }

      StringBuilder result = new StringBuilder();
      Node current = root;

      for (char bit : encoded.toCharArray()) {
        int idx = bit - '0';
        current = current.children[idx];

        if (current == null) {
          throw new IllegalArgumentException("Invalid encoded string");
        }

        if (current.symbol != null) {
          result.append(current.symbol);
          current = root;
        }
      }

      if (current != root) {
        throw new IllegalArgumentException("Invalid encoded string");
      }

      return result.toString();
    }
  }
}
