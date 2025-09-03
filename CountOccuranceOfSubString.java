package com.day1.lab1;

public class CountOccuranceOfSubString {
	
	public static int countOccurrences(String text, String sub) {
	        if (text == null || sub == null || sub.isEmpty()) {
	            return 0;
	        }

	        int count = 0;
	        int idx = 0;
	        while ((idx = text.indexOf(sub, idx)) != -1) {
	            count++;
	            idx += sub.length();
	        }
	        return count;
	    }

	    public static void main(String[] args) {
	        String text1 = "java is java and java rocks";
	        String sub1 = "java";
	        System.out.println("Input: \"" + text1 + "\", \"" + sub1 + "\"");
	        System.out.println("Output: " + countOccurrences(text1, sub1)); // Expected: 3

	      }
	}