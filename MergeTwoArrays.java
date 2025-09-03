package com.day1.lab1;

import java.util.Arrays;

public class MergeTwoArrays {

	   public static void main(String[] args) {
	        int[] array1 = {101, 102};
	        int[] array2 = {201, 202};

	        System.out.println("Array 1: " + Arrays.toString(array1));
	        System.out.println("Array 2: " + Arrays.toString(array2));

	        
	        int[] mergedArray1 = mergeArraysManual(array1, array2);
	        System.out.println("Merged Array (Manual Loop): " + Arrays.toString(mergedArray1));

	      
	        int[] mergedArray2 = mergeArraysSystemCopy(array1, array2);
	        System.out.println("Merged Array (System.arraycopy): " + Arrays.toString(mergedArray2));

	        
	        int[] mergedArray3 = mergeArraysStream(array1, array2);
	        System.out.println("Merged Array (Stream API): " + Arrays.toString(mergedArray3));

	       
	    }
	    public static int[] mergeArraysManual(int[] arr1, int[] arr2) {
	        
	        int mergedLength = arr1.length + arr2.length;
	        int[] mergedArray = new int[mergedLength];

	      
	        for (int i = 0; i < arr1.length; i++) {
	            mergedArray[i] = arr1[i];
	        }

	        
	        for (int i = 0; i < arr2.length; i++) {
	            mergedArray[arr1.length + i] = arr2[i];
	        }

	        return mergedArray;
	    }

	  
	    public static int[] mergeArraysSystemCopy(int[] arr1, int[] arr2) {
	        int mergedLength = arr1.length + arr2.length;
	        int[] mergedArray = new int[mergedLength];

	        
	        System.arraycopy(arr1, 0, mergedArray, 0, arr1.length);

	        
	        System.arraycopy(arr2, 0, mergedArray, arr1.length, arr2.length);

	        return mergedArray;
	    }

	   
	    public static int[] mergeArraysStream(int[] arr1, int[] arr2) {
	        return java.util.stream.IntStream.concat(Arrays.stream(arr1), Arrays.stream(arr2))
	                                         .toArray();
	    }
	}