package com.day1.lab2;

import java.util.Scanner;

public class IterativeWordCounter {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the paragraph:");
        String paragraph = scanner.nextLine();

        System.out.println("Enter the word to count:");
        String wordToCount = scanner.nextLine();

        int count = countWordOccurrences(paragraph, wordToCount);

        System.out.println("The word \"" + wordToCount + "\" appears " + count + " times in the paragraph.");

        scanner.close();
    }

   
    public static int countWordOccurrences(String paragraph, String wordToCount) {
        if (paragraph == null || wordToCount == null || wordToCount.isEmpty()) {
            return 0;
        }

        int count = 0;
        // Convert both to lowercase for case-insensitive comparison
        String lowerCaseParagraph = paragraph.toLowerCase();
        String lowerCaseWordToCount = wordToCount.toLowerCase();

        // Iterate through the paragraph to find occurrences
        // We iterate up to (paragraph length - word length) to ensure a full word can be matched
        for (int i = 0; i <= lowerCaseParagraph.length() - lowerCaseWordToCount.length(); i++) {
            boolean match = true;
            // Check if the substring from 'i' matches the wordToCount
            for (int j = 0; j < lowerCaseWordToCount.length(); j++) {
                char paragraphChar = lowerCaseParagraph.charAt(i + j);
                char wordChar = lowerCaseWordToCount.charAt(j);

                switch (paragraphChar) {
                    case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                         '0', '1', '2', '3', '4', '5', '6', '7', '8', '9': // Include common characters for demonstration
                        if (paragraphChar != wordChar) {
                            match = false;
                        }
                        break;
                    default: // For any other characters or symbols
                        if (paragraphChar != wordChar) {
                            match = false;
                        }
                        break;
                }

                if (!match) {
                    break; // No need to check further if a character doesn't match
                }
            }

            if (match) {
                count++;
            }
        }
        return count;
    }
}