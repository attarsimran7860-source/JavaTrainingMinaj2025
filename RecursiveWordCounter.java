package com.day1.lab2;

import java.util.Scanner;


public class RecursiveWordCounter {

	 public int countWordOccurrences(String paragraph, String targetWord) {
	        if (paragraph == null || targetWord == null || targetWord.isEmpty()) {
	            return 0; // Handle edge cases: null inputs or empty target word
	        }
	        // Normalize the target word to lowercase once for efficiency
	        String lowerTargetWord = targetWord.toLowerCase();
	        return countRecursive(paragraph.toLowerCase(), lowerTargetWord, 0, 0);
	    }

	 
	    private int countRecursive(String lowerParagraph, String lowerTargetWord, int currentIndex, int count) {
	        
	        if (currentIndex + lowerTargetWord.length() > lowerParagraph.length()) {
	            return count;
	        }

	        // Extract a substring from the current index with the length of the target word.
	        String substring = lowerParagraph.substring(currentIndex, currentIndex + lowerTargetWord.length());

	        
	        boolean match = true;
	        for (int i = 0; i < lowerTargetWord.length(); i++) {
	            char targetChar = lowerTargetWord.charAt(i);
	            char paragraphChar = substring.charAt(i);

	            //enhance switch
	            switch (Character.toLowerCase(targetChar)) { // Normalize target to lowercase (redundant here due to pre-normalization)
	                case 'a':
	                case 'b':
	                case 'c':
	                    if (Character.toLowerCase(paragraphChar) != Character.toLowerCase(targetChar)) {
	                        match = false;
	                        break;
	                    }
	                    break;
	                
	                default:
	                    if (Character.toLowerCase(paragraphChar) != Character.toLowerCase(targetChar)) {
	                        match = false;
	                        break;
	                    }
	            }
	            if (!match) {
	                break;
	            }
	        }

	        if (match) {
	            
	            return countRecursive(lowerParagraph, lowerTargetWord, currentIndex + 1, count + 1);
	        } else {
	            // If no match, continue searching from the next character.
	            return countRecursive(lowerParagraph, lowerTargetWord, currentIndex + 1, count);
	        }
	    }

	    public static void main(String[] args) {
	    	RecursiveWordCounter counter = new RecursiveWordCounter();
	    	System.out.println("Enter paragraph...");
	    	Scanner sc=new Scanner(System.in);
	    	String passage1=sc.nextLine();
	    	

	        //String passage1 = "The quick brown fox jumps over the lazy dog. The fox is quite quick.";
	    	System.out.println("Enter word you want to search...");
	        String word1 = sc.next();
	        System.out.println("Paragraph: \"" + passage1 + "\"");
	        System.out.println("Target word: \"" + word1 + "\"");
	        System.out.println("Occurrences: " + counter.countWordOccurrences(passage1, word1)); // Expected: 3

	        
	    }
	}