package com.day1.lab2;

import java.util.*;
import java.util.List;

public class NestedIntSumIterative {

   
    public static int sumNestedListIterative(List<Object> nestedList) {
        int sum = 0;
        
        Stack<List<Object>> stack = new Stack<>();

       
        stack.push(nestedList);

        while (!stack.isEmpty()) {
            
            List<Object> currentList = stack.pop();

         
            for (Object element : currentList) {
                if (element instanceof Integer) {
                    
                    sum += (Integer) element;
                } else if (element instanceof List) {
                    
                    stack.push((List<Object>) element);
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        // Example 1: {1, {2, 3}, {4, {5}}}
        List<Object> nestedList1 = new ArrayList<>();
        nestedList1.add(1);

        List<Object> innerList1_1 = new ArrayList<>();
        innerList1_1.add(2);
        innerList1_1.add(3);
        nestedList1.add(innerList1_1);

        List<Object> innerList1_2 = new ArrayList<>();
        innerList1_2.add(4);

        List<Object> innerList1_2_1 = new ArrayList<>();
        innerList1_2_1.add(5);
        innerList1_2.add(innerList1_2_1);
        nestedList1.add(innerList1_2);

        System.out.println("Nested List 1: " + nestedList1);
        System.out.println("Sum of Nested List 1 (Iterative): " + sumNestedListIterative(nestedList1)); // Expected: 1 + 2 + 3 + 4 + 5 = 15

    }
}