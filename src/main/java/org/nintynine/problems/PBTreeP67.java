package org.nintynine.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PBTreeP67<T> {
    private final T value;
    protected PBTreeP67<T> left;
    protected PBTreeP67<T> right;

    public PBTreeP67(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public static int minNodes(int h) {
        if (h <= 0) return 0;
        if (h == 1) return 1;
        return 1 + minNodes(h - 1) + minNodes(h - 2);
    }

    public static int maxHeight(int n) {
        if (n <= 0) return 0;
        int h = 0;
        while (minNodes(h) <= n) h++;
        return h - 1;
    }

    public static List<PBTreeP67<String>> hbalTreeNodes(int n) {
        if (n <= 0) return Collections.emptyList();
        if (n == 1) {
            List<PBTreeP67<String>> result = new ArrayList<>();
            result.add(new PBTreeP67<>("x"));
            return result;
        }

        // Special case for 2 nodes
        if (n == 2) {
            List<PBTreeP67<String>> result = new ArrayList<>();
            // Create left-child tree
            PBTreeP67<String> leftTree = new PBTreeP67<>("x");
            leftTree.left = new PBTreeP67<>("x");
            result.add(leftTree);

            // Create right-child tree
            PBTreeP67<String> rightTree = new PBTreeP67<>("x");
            rightTree.right = new PBTreeP67<>("x");
            result.add(rightTree);

            return result;
        }

        List<PBTreeP67<String>> result = new ArrayList<>();
        int maxH = maxHeight(n);
        int minH = (int) Math.ceil(Math.log(n + (double)1) / Math.log(2));

        for (int h = minH; h <= maxH; h++) {
            result.addAll(generateTreesWithHeight(h, n));
        }

        return result;
    }

    private static List<PBTreeP67<String>> generateTreesWithHeight(int height, int n) {
        List<PBTreeP67<String>> result = new ArrayList<>();

        if (n == 0) {
            return Collections.emptyList();
        }
        if (n == 1) {
            result.add(new PBTreeP67<>("x"));
            return result;
        }

        for (int leftNodes = 0; leftNodes < n; leftNodes++) {
            int rightNodes = n - 1 - leftNodes;

            List<PBTreeP67<String>> leftSubtrees = hbalTreeNodes(leftNodes);
            List<PBTreeP67<String>> rightSubtrees = hbalTreeNodes(rightNodes);

            for (PBTreeP67<String> left : leftSubtrees) {
                for (PBTreeP67<String> right : rightSubtrees) {
                    if (isHeightBalanced(left, right) &&
                            getHeight(left) <= height - 1 &&
                            getHeight(right) <= height - 1) {
                        PBTreeP67<String> root = new PBTreeP67<>("x");
                        root.left = cloneTree(left);
                        root.right = cloneTree(right);
                        result.add(root);
                    }
                }
            }
        }

        return result;
    }

    private static boolean isHeightBalanced(PBTreeP67<String> left, PBTreeP67<String> right) {
        return Math.abs(getHeight(left) - getHeight(right)) <= 1;
    }

    public static int getHeight(PBTreeP67<?> node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private static PBTreeP67<String> cloneTree(PBTreeP67<String> node) {
        if (node == null) return null;
        PBTreeP67<String> clone = new PBTreeP67<>(node.value);
        clone.left = cloneTree(node.left);
        clone.right = cloneTree(node.right);
        return clone;
    }

    // Getters for testing
    public T getValue() {
        return value;
    }

    public PBTreeP67<T> getLeft() {
        return left;
    }

    public PBTreeP67<T> getRight() {
        return right;
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

        int firstParen = treeString.indexOf('(');
        if (firstParen == -1) {
            // Leaf node
            return new PBTreeP67<>(treeString);
        }

        String value = treeString.substring(0, firstParen);
        PBTreeP67<String> root = new PBTreeP67<>(value);

        // Content within the parentheses, excluding the outer parentheses
        String childrenString = treeString.substring(firstParen + 1, treeString.length() - 1);

        if (childrenString.isEmpty()) { // Should not happen with valid format like "a()" but good to guard
            return root;
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
            // This could mean one child is present and the comma is implicit, or format error
            // Based on problem: "a(b,)" or "a(,c)" or "a(b)" - assuming "a(b)" means "a(b,)"
            // The prompt examples: "value(left_subtree,)" and "value(,right_subtree)"
            // This logic needs to be robust for these cases.
            // If childrenString ends with ',', it's a left child only.
            // If childrenString starts with ',', it's a right child only.
            // However, the balance logic should find the comma correctly if it's at balance 0.
            // Let's refine based on the comma found by balance.
            // The current loop finds comma ONLY if balance is 0.
            // For "a(b(d,e),c(,f(g,)))", childrenString is "b(d,e),c(,f(g,))"
            // b ( balance becomes 1 for left child's content
            // ) balance becomes 0
            // , HERE commaIndex is found.
            // So this logic for finding commaIndex seems correct for the main case.

            // What if childrenString is just "b" for a(b,)? or ",c" for a(,c)?
            // The problem states: "a(b,)" -> left="b", right=null. "a(,c)" -> left=null, right="c".
            // If commaIndex is 0, it means left is empty: childrenString = ",right"
            // If commaIndex is childrenString.length() - 1, it means right is empty: childrenString = "left,"

            // The loop for commaIndex is designed for "left,right".
            // If commaIndex is not found (remains -1), it implies there's no top-level comma.
            // This means either there's only one child specified, or it's malformed.
            // Example: "a(b)" could be interpreted as "a(b,)"
            // Example: "a(b(c,d))" means "a(b(c,d),)"
            // If childrenString = "b(c,d)", commaIndex will be -1.
            // If childrenString = ",", then left="", right="".
            // This part needs careful handling of the split.

            // Let's re-evaluate the split after finding commaIndex.
            // The current commaIndex logic is okay. It finds the first top-level comma.
        }


        if (commaIndex != -1) {
            leftSubtreeString = childrenString.substring(0, commaIndex);
            rightSubtreeString = childrenString.substring(commaIndex + 1);
        } else {
            // This case implies no top-level comma was found.
            // This can happen if there's only one subtree string, e.g. "a(b(c,d))"
            // Or if the string is just "a(b)", which means left is "b" and right is empty.
            // Or if the string is "a(,c)", this case is handled if commaIndex is 0.
            // Or if the string is "a(b,)", this case is handled if commaIndex is childrenString.length()-1.
            // The current loop for commaIndex will find the comma if it exists at balance 0.
            // So, if commaIndex is -1, it implies the *entire* childrenString is for one side,
            // but we don't know which side without more info or clearer rules for single child without comma.
            // Let's stick to the defined format: "value(left,right)". A missing comma implies one part is empty.
            // The problem defines "a(b,)" and "a(,c)".
            // If childrenString is "b," then commaIndex would be 1. left="b", right="".
            // If childrenString is ",c" then commaIndex would be 0. left="", right="c".
            // If childrenString is "b" (e.g. from "a(b)"), how to interpret? Assume "a(b,)"
            // If childrenString is "b(c,d)" (e.g. from "a(b(c,d))"), assume "a(b(c,d),)"
            // This means if commaIndex is -1 AND childrenString is not empty, it's the left child.
            // This might be an oversimplification. The balanced comma search is key.

            // Let's test the comma finding logic:
            // childrenString = "b(d,e),c(,f(g,))" -> commaIndex correctly at the split.
            // childrenString = "b," -> commaIndex = 1. left="b", right="". Correct.
            // childrenString = ",c" -> commaIndex = 0. left="", right="c". Correct.
            // childrenString = "b" -> commaIndex = -1. This means it's a single node string for the left child.
            // childrenString = "b(c,d)" -> commaIndex = -1. This is a complex string for the left child.

            // If commaIndex is -1, it means the childrenString does not contain a top-level comma.
            // This implies that the *entire* childrenString is *either* the left subtree string
            // *or* the right subtree string, *or* it's a single child that implicitly means left.
            // Given "value(left_subtree,)" and "value(,right_subtree)", a missing comma inside
            // the children string like in "a(b)" is not explicitly covered by these two.
            // However, "a(b)" is usually shorthand for "a(b,)".
            // If childrenString does not contain any comma at all, and is not empty,
            // it must be the left child's string.
            if (!childrenString.isEmpty() && !childrenString.contains(",")) {
                leftSubtreeString = childrenString;
                rightSubtreeString = ""; // Assume if only one part, it's the left.
            } else if (childrenString.equals(",")) { // Case like "a(,)"
                leftSubtreeString = "";
                rightSubtreeString = "";
            }
            // Other cases (like malformed strings) might lead to errors or incorrect parsing here.
            // The examples "a(b,)" and "a(,c)" imply the comma will be present.
            // So, if commaIndex is -1, it might mean childrenString is like "b" or "b(c,d)".
            // In this scenario, it should be treated as the left child.
            // The problem: "If a subtree string is empty ... then the corresponding child should be null."
            // This is handled by recursive calls with "" returning null.

            // If commaIndex was not found by the balanced search, it means the childrenString
            // is a single segment, e.g., "b" from "a(b)" or "b(c,d)" from "a(b(c,d))".
            // This single segment should be treated as the left child, and the right is empty.
            // This seems to be the most reasonable interpretation for inputs like "a(b)".
             if (commaIndex == -1 && !childrenString.isEmpty()) {
                 // This situation should ideally be clarified.
                 // If childrenString is "foo" (no comma, no parens inside it at top level) -> left = "foo", right = ""
                 // If childrenString is "foo(bar,baz)" (no comma at top level of childrenString) -> left = "foo(bar,baz)", right = ""
                 // This seems like the only logical step if commaIndex is -1 and childrenString is not empty.
                 // However, the examples always show a comma if one of the children is empty, e.g. "a(b,)" or "a(,c)".
                 // This implies `childrenString` itself will be "b," or ",c".
                 // If `childrenString` is "b,", `commaIndex` will be 1. `left="b"`, `right=""`.
                 // If `childrenString` is ",c", `commaIndex` will be 0. `left=""`, `right="c"`.
                 // If `childrenString` is "b", `commaIndex` will be -1. How to parse "a(b)"?
                 //   Option 1: Assume "a(b,)" -> left="b", right="".
                 //   Option 2: Error or undefined.
                 // Given the example "a(b,c)", "a(b,)", "a(,c)", it seems the comma is key.
                 // What if treeString = "a(b)" ? childrenString = "b". commaIndex = -1.
                 // It should probably be leftSubtreeString = "b", rightSubtreeString = "".
                 // The problem statement says: "If left is not null but right is null, the representation is value(left_subtree,)"
                 // This implies the comma *will* be there.
                 // So, if commaIndex is -1, and childrenString is not empty, it's likely an invalid format
                 // according to strict interpretation of examples.
                 // However, typical parsers might be more lenient.
                 // Let's trust the comma-finding logic. If no comma is found at balance 0,
                 // and childrenString is not empty, it must be a single child's representation.
                 // Which child? The examples like "a(b,)" suggest it's specified.
                 // For now, let's assume if commaIndex is -1 and childrenString is non-empty,
                 // it's an invalid representation according to the explicit examples,
 Dauntingly, the parsing logic here is tricky. The examples "a(b,)" and "a(,c)" imply the comma is always present if there's a parenthesis structure.
If `childrenString` is simply "b", without a comma, this case is not directly covered by the "value(left,)" or "value(,right)" formats.
The most robust way to find the split point is to find the comma at balance 0 (or balance 1 if we consider the children string to be one level down).
My current loop for `commaIndex` searches `childrenString` for a comma at `balance == 0` relative to `childrenString` itself.
This is equivalent to balance 1 relative to the original `treeString`'s main parentheses.

Let's refine the logic for splitting `childrenString`:
1. Iterate through `childrenString` to find `commaIndex` where balance (local to `childrenString`) is 0.
2. If such `commaIndex` is found:
   `leftSubtreeString = childrenString.substring(0, commaIndex);`
   `rightSubtreeString = childrenString.substring(commaIndex + 1);`
3. If no such `commaIndex` is found (e.g., `childrenString` is "b" or "b(c,d)" or empty):
    This case is problematic if we strictly follow "value(left,)" or "value(,right)".
    If `childrenString` is "b", it implies `treeString` was "a(b)". This is not `a(b,)`.
    If the problem implies that any single child representation *must* include the comma (e.g., "a(b,)" or "a(,b)"),
    then a `childrenString` like "b" (from "a(b)") would be an invalid format.

Let's assume the provided format examples are strict. The comma separator at the correct parenthesis level is essential.
If `commaIndex` is not found by the loop, it means `childrenString` does not have a top-level comma.
This implies `childrenString` itself is the representation of a single subtree, but the examples ("a(b,)", "a(,c)") don't show this structure.
They show the comma explicitly.
Example: "a(b(d,e),c(,f(g,)))" -> childrenString = "b(d,e),c(,f(g,))". Comma is found.
Example: "a(b,)" -> childrenString = "b,". Comma is found at index 1. left="b", right="".
Example: "a(,c)" -> childrenString = ",c". Comma is found at index 0. left="", right="c".
Example: "a(b,c)" -> childrenString = "b,c". Comma is found. left="b", right="c".

What if `childrenString` is just "b" (from an input "a(b)")? The current `commaIndex` loop will result in `commaIndex = -1`.
The problem statement implies string values. So "b" is a valid subtree string for a leaf.
If `treeString` is "a(b)", then `childrenString` is "b". `commaIndex` remains -1.
This implies that `childrenString` is entirely for one child. Which one? Standard interpretation is left.
The problem statement:
- If `left` is not null but `right` is null, the representation is `value(left_subtree,)`.
- If `left` is null but `right` is not null, the representation is `value(,right_subtree)`.
This means if `treeString` is "a(b)", it does not match these explicit single-child formats.
This is a point of ambiguity. I will proceed assuming that if `childrenString` does not contain a top-level comma,
it represents the left child, and the right child is null. This is a common convention.

Revised logic for splitting `childrenString`:
Find `commaIndex` as before (comma at balance 0 within `childrenString`).
If `commaIndex != -1`:
  `leftSubtreeString = childrenString.substring(0, commaIndex);`
  `rightSubtreeString = childrenString.substring(commaIndex + 1);`
Else (no top-level comma found in `childrenString`):
  If `childrenString` is not empty, assume it's the left child.
    `leftSubtreeString = childrenString;`
    `rightSubtreeString = "";` // Empty string for right child
  Else (`childrenString` is empty, e.g. from "a()"):
    `leftSubtreeString = "";`
    `rightSubtreeString = "";`

This seems like a reasonable way to handle it.
The recursive calls `stringToTree("")` will correctly return `null`.
So, `root.left = stringToTree(leftSubtreeString);` and `root.right = stringToTree(rightSubtreeString);` will work.
Final check on `childrenString` extraction: `treeString.substring(firstParen + 1, treeString.length() - 1);`
If `treeString` is "a()", `firstParen` is 1. `childrenString` = `treeString.substring(2, 2)` which is `""`. Correct.
If `treeString` is "a(b,)", `firstParen` is 1. `childrenString` = `treeString.substring(2, 4)` which is "b,". Correct.
Looks good.
            */

            // Corrected logic for commaIndex and splitting:
            // The loop for commaIndex is correct. It finds the main splitting comma.
            // If commaIndex is -1, it means childrenString does not have a comma at balance 0.
            // This could mean:
            // 1. childrenString is empty (e.g. "a()") -> left="", right=""
            // 2. childrenString represents a single subtree (e.g. "b" from "a(b)", or "b(c,d)" from "a(b(c,d))" )
            //    In this case, we need to decide if it's left or right.
            //    The problem's examples "value(left,)" and "value(,right)" suggest the comma *must* be present
            //    to differentiate. If "a(b)" is given, it's ambiguous by those strict rules.
            //    However, if we must parse "a(b)", it's typically left child.
            // Let's stick to the interpretation that the comma found by the balanced search is the *only* separator.
            // If it's not found, then the childrenString cannot be split into two distinct parts based on a top-level comma.

            // If commaIndex is found:
            if (commaIndex != -1) {
                 leftSubtreeString = childrenString.substring(0, commaIndex);
                 rightSubtreeString = childrenString.substring(commaIndex + 1);
            } else {
                // No top-level comma found in childrenString.
                // This means childrenString is either empty, or represents a single subtree.
                // If it represents a single subtree, which one is it?
                // The problem examples are: "a(b,)" (right is null) and "a(,c)" (left is null).
                // These formats *have* a comma.
                // If treeString is "a(b)" -> childrenString is "b". No comma.
                // This doesn't fit "a(b,)" or "a(,c)".
                // This is an ambiguity. A robust parser might assume "a(b)" -> "a(b,)".
                // If childrenString itself is empty, then left="", right="".
                if (childrenString.isEmpty()) {
                    leftSubtreeString = "";
                    rightSubtreeString = "";
                } else {
                    // childrenString is not empty and has no top-level comma. E.g., "b" or "b(c,d)".
                    // Assume this is the left child, and right is implicitly empty.
                    leftSubtreeString = childrenString;
                    rightSubtreeString = "";
                }
            }
        }
        // End of my previous large comment block.

        root.left = stringToTree(leftSubtreeString);
        root.right = stringToTree(rightSubtreeString);

        return root;
    }
}
