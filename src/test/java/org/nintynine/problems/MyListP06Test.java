package org.nintynine.problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MyListP06Test {
    @Test
    void isPalindrome() {
        MyListP06<String> palindrome = new MyListP06<>("x", "a", "m", "a", "x");
        assertTrue(palindrome.isPalindrome());

        MyListP06<String> notPalindrome = new MyListP06<>("a", "b", "c");
        assertFalse(notPalindrome.isPalindrome());

        MyListP06<Integer> numPalindrome = new MyListP06<>(1, 2, 3, 2, 1);
        assertTrue(numPalindrome.isPalindrome());

        MyListP06<String> singleElement = new MyListP06<>("a");
        assertTrue(singleElement.isPalindrome());

        MyListP06<String> empty = new MyListP06<>();
        assertTrue(empty.isPalindrome());

        // Additional test for null elements
        MyListP06<String> withNulls = new MyListP06<>(null, "a", null);
        assertThrows(NullPointerException.class,withNulls::isPalindrome);
    }

}