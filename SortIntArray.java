package com.day1.lab1;

import java.util.Arrays;

public class SortIntArray {

	 public static void main(String[] args) {
	        int[] numbers = {55, 90, 12, 67};

	        System.out.print("Original array: ");
	        printArray(numbers);

	       
	        int[] sortedNumbers1 = Arrays.copyOf(numbers, numbers.length); 
	        Arrays.sort(sortedNumbers1);
	        System.out.print("Sorted array (Arrays.sort): ");
	        printArray(sortedNumbers1);

	        
	        int[] sortedNumbers2 = Arrays.copyOf(numbers, numbers.length); 
	        bubbleSort(sortedNumbers2);
	        System.out.print("Sorted array (Bubble Sort): ");
	        printArray(sortedNumbers2);

	       
	        int[] sortedNumbers3 = Arrays.copyOf(numbers, numbers.length);
	        selectionSort(sortedNumbers3);
	        System.out.print("Sorted array (Selection Sort): ");
	        printArray(sortedNumbers3);
	    }

	   
	    public static void printArray(int[] arr) {
	        System.out.print("{");
	        for (int i = 0; i < arr.length; i++) {
	            System.out.print(arr[i]);
	            if (i < arr.length - 1) {
	                System.out.print(", ");
	            }
	        }
	        System.out.println("}");
	    }

	    /**
	     * Implements the Bubble Sort algorithm.
	     * Time Complexity: O(n^2)
	     */
	    public static void bubbleSort(int[] arr) {
	        int n = arr.length;
	        for (int i = 0; i < n - 1; i++) {
	            for (int j = 0; j < n - i - 1; j++) {
	                // Swap if the element found is greater than the next element
	                if (arr[j] > arr[j + 1]) {
	                    int temp = arr[j];
	                    arr[j] = arr[j + 1];
	                    arr[j + 1] = temp;
	                }
	            }
	        }
	    }

	  
	    public static void selectionSort(int[] arr) {
	        int n = arr.length;
	        for (int i = 0; i < n - 1; i++) {
	            // Find the minimum element in unsorted array
	            int min_idx = i;
	            for (int j = i + 1; j < n; j++) {
	                if (arr[j] < arr[min_idx]) {
	                    min_idx = j;
	                }
	            }
	            // Swap the found minimum element with the first element
	            int temp = arr[min_idx];
	            arr[min_idx] = arr[i];
	            arr[i] = temp;
	        }
	    }
	}
