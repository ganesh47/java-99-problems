package org.nintynine.problems;

/**
 * P69: Convert a binary tree to and from a dotstring representation.
 *
 * <p>The dotstring syntax in Backus–Naur form (BNF) is:</p>
 * <pre>
 *   tree ::= '.' | node tree tree
 *   node ::= 'a' | 'b' | ... | 'z'
 * </pre>
 *
 * <p>A preorder traversal is used where '.' represents an empty subtree.</p>
 */
public class BTreeP69 {

    private BTreeP69() {
    }

    /** A simple binary tree node containing a lowercase character. */
    public static class BTreeP69Node {
        char value;
        BTreeP69Node left;
        BTreeP69Node right;

        public BTreeP69Node(char value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof BTreeP69Node other)) return false;
            return value == other.value &&
                   java.util.Objects.equals(left, other.left) &&
                   java.util.Objects.equals(right, other.right);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(value, left, right);
        }

        @Override
        public String toString() {
            if (left == null && right == null) {
                return Character.toString(value);
            }
            return value + "(" +
                    (left == null ? "NIL" : left.toString()) + "," +
                    (right == null ? "NIL" : right.toString()) + ")";
        }
    }

    /**
     * Convert the given tree into its dotstring representation.
     *
     * @param node the root node
     * @return dotstring representation
     */
    public static String dotstring(BTreeP69Node node) {
        if (node == null) {
            return ".";
        }
        return node.value + dotstring(node.left) + dotstring(node.right);
    }

    /**
     * Parse a tree from the given dotstring.
     *
     * @param str dotstring representation
     * @return root node of the parsed tree, or {@code null} for an empty tree
     */
    public static BTreeP69Node tree(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        Parser parser = new Parser(str);
        return parser.parse();
    }

    private static class Parser {
        private final String s;
        private int index;

        Parser(String s) {
            this.s = s;
        }

        BTreeP69Node parse() {
            if (index >= s.length()) {
                return null;
            }
            char c = s.charAt(index++);
            if (c == '.') {
                return null;
            }
            BTreeP69Node node = new BTreeP69Node(c);
            node.left = parse();
            node.right = parse();
            return node;
        }
    }
}
