package org.nintynine.problems;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyListP04Test {
    
    @Test
    void testEmptyListLength() {
        MyListP04<String> list = new MyListP04<>();
        assertEquals(0, list.length());
    }
    
    @Test
    void testSingleElementLength() {
        MyListP04<Integer> list = new MyListP04<>(1);
        assertEquals(1, list.length());
    }
    
    @Test
    void testMultipleElementsLength() {
        MyListP04<String> list = new MyListP04<>("a", "b", "c", "d", "e");
        assertEquals(5, list.length());
    }
    
    @Test
    void testListWithNullValues() {
        MyListP04<String> list = new MyListP04<>("a", null, "c", null);
        assertEquals(4, list.length());
    }
    
    @Test
    void testLengthWithDifferentTypes() {
        MyListP04<Integer> intList = new MyListP04<>(1, 2, 3);
        assertEquals(3, intList.length());
        
        MyListP04<Double> doubleList = new MyListP04<>(1.0, 2.0, 3.0, 4.0);
        assertEquals(4, doubleList.length());
        
        MyListP04<Boolean> boolList = new MyListP04<>(true, false, true);
        assertEquals(3, boolList.length());
    }
}