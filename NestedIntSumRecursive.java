package com.day1.lab2;

import java.util.ArrayList;
import java.util.List;

public class NestedIntSumRecursive {

   
    public static int sumNestedList(List<Object> nestedList) {
        int totalSum = 0; 

        
        for (Object element : nestedList) {
            if (element instanceof Integer) {
                
                totalSum += (Integer) element;
            } else if (element instanceof List) {
                
                totalSum += sumNestedList((List<Object>) element);
            }
            
        }

        return totalSum; 
    }

    public static void main(String[] args) {
       
        List<Object> example1 = new ArrayList<>();
        example1.add(1); 

        List<Object> innerList1_2_3 = new ArrayList<>();
        innerList1_2_3.add(2);
        innerList1_2_3.add(3);
        example1.add(innerList1_2_3); 

        List<Object> innerList1_4_5 = new ArrayList<>();
        innerList1_4_5.add(4); 

        List<Object> innerList1_5 = new ArrayList<>();
        innerList1_5.add(5);
        innerList1_4_5.add(innerList1_5); 
        example1.add(innerList1_4_5); 

        System.out.println("Structure: " + example1);
        System.out.println("Sum: " + sumNestedList(example1)); 

        }
}