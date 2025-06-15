package org.nintynine.problems;

public class PBTreeP67<T> {
    private final T value;
    private PBTreeP67<T> left;
    private PBTreeP67<T> right;

    public PBTreeP67(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public T getValue() {
        return value;
    }

    public PBTreeP67<T> getLeft() {
        return left;
    }

    public PBTreeP67<T> getRight() {
        return right;
    }

    // Setter methods might be needed by test code or stringToTree if it doesn't handle children in constructor
    public void setLeft(PBTreeP67<T> left) {
        this.left = left;
    }

    public void setRight(PBTreeP67<T> right) {
        this.right = right;
    }

    public String treeToString() {
        StringBuilder sb = new StringBuilder();
        if (value == null) {
            sb.append("null");
        } else {
            sb.append(value.toString());
        }

        if (left != null || right != null) {
            sb.append("(");
            if (left != null) {
                sb.append(left.treeToString());
            }
            sb.append(",");
            if (right != null) {
                sb.append(right.treeToString());
            }
            sb.append(")");
        }
        return sb.toString();
    }

    public static PBTreeP67<String> stringToTree(String treeString) {
        if (treeString == null || treeString.isEmpty()) {
            return null;
        }

        int parenIndex = treeString.indexOf('(');

        if (parenIndex == -1) { // Leaf node
            return new PBTreeP67<>(treeString);
        }

        String value = treeString.substring(0, parenIndex);
        PBTreeP67<String> root = new PBTreeP67<>(value);

        // Extract children string: content between the first '(' and the last ')'
        // Ensure the last char is ')' for valid format, otherwise it might be malformed or a leaf node that happens to have '(' in its value
        if (treeString.charAt(treeString.length() - 1) != ')') {
            // This case might indicate a malformed string or a leaf node whose value contains '('.
            // For simplicity, if not ending with ')', treat as leaf node (value is the whole string).
            // Or, one might throw an IllegalArgumentException here.
            // Based on problem description, "a(b,c)" implies structure. "a(b" is not standard.
            // If "value(" is the string, it's likely malformed.
            // Let's assume valid inputs or that such cases are treated as leaf if no matching ')'
            // For robustness against "value(" without closing, we could check if treeString.charAt(treeString.length() - 1) == ')'
            // However, the problem implies a structure like a(L,R) or a.
            // If we found '(' but no matching ')', it's an issue. The current logic assumes matching ')'
            // The prompt example a(b(d,e),c(,f(g,))) implies last char is ')' for non-leaf.
             return new PBTreeP67<>(treeString); // Fallback or error
        }

        String childrenString = treeString.substring(parenIndex + 1, treeString.length() - 1);

        if (childrenString.isEmpty()) { // E.g. "a()" - implies no children, though problem implies "a" for this
            return root; // Children are null by default
        }

        int balance = 0;
        int commaIndex = -1;

        for (int i = 0; i < childrenString.length(); i++) {
            if (childrenString.charAt(i) == '(') {
                balance++;
            } else if (childrenString.charAt(i) == ')') {
                balance--;
            } else if (childrenString.charAt(i) == ',' && balance == 0) {
                commaIndex = i;
                break;
            }
        }

        String leftSubtreeString = "";
        String rightSubtreeString = "";

        if (commaIndex == -1) {
            // No comma found at balance 0. This could mean:
            // 1. Only a left child is present (e.g., "a(b)"). Problem implies "a(b,)"
            // 2. Malformed string.
            // Assuming based on "a(b,)" and "a(,c)" that comma is always present if there's structure.
            // If childrenString is "b", it's left child. " ,c " implies left is null.
            // The prompt implies "a(b,)" or "a(,c)". A single item "b" inside "a(b)" is ambiguous
            // based on strict "value(left,right)" form. Let's assume it's left if no comma.
            // The provided solution for P67 does this: if no comma, all content is left child.
            leftSubtreeString = childrenString;
        } else {
            leftSubtreeString = childrenString.substring(0, commaIndex);
            rightSubtreeString = childrenString.substring(commaIndex + 1);
        }

        root.setLeft(stringToTree(leftSubtreeString));
        root.setRight(stringToTree(rightSubtreeString));

        return root;
    }
}
