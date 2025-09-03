package com.day1.lab1;

public class PallindromeORnot {
	
	public static boolean isPalindrome(String str) {
        
        String cleanedStr = str.replaceAll("\\s+", "").toLowerCase();

        int left = 0;
        int right = cleanedStr.length() - 1;

        while (left < right) {
            if (cleanedStr.charAt(left) != cleanedStr.charAt(right)) {
                return false; 
            }
            left++;
            right--;
        }
        return true; 
    }

    public static void main(String[] args) {
        String testString1 = "level";
      
        System.out.println("Is \"" + testString1 + "\" a palindrome? " + isPalindrome(testString1)); // Expected: true
      
    }
}