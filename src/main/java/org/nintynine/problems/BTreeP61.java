package org.nintynine.problems;

public class BTreeP61<T> extends BTreeP60<T> {
    
    public BTreeP61(T value) {
        super(value);
    }
    
    public static <T> int countLeaves(BTreeP61<T> tree) {
        if (tree == null) {
            return 0;
        }
        
        // A leaf is a node with no children
        if (tree.getLeft() == null && tree.getRight() == null) {
            return 1;
        }
        
        // Recursively count leaves in left and right subtrees
        return countLeaves((BTreeP61<T>) tree.getLeft()) + 
               countLeaves((BTreeP61<T>) tree.getRight());
    }
}
