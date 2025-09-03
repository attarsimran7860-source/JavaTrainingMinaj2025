package com.day1.lab1;

public class AfterWhiteSpaceFirstLetterCapital{

	   public static void main(String[] args) {
	        String text = " java utility LAB";
	        String formattedText = formatString(text); 
	        System.out.println(formattedText); 
	    }

	    
	    public static String formatString(String text) {
	        
	        if (text == null || text.isEmpty()) {
	            return text;
	        }

	        StringBuilder result = new StringBuilder();
	        
	        String[] words = text.split("\\s+");

	        for (String word : words) {
	            if (!word.isEmpty()) { 
	                
	                String firstChar = word.substring(0, 1).toUpperCase();
	               
	                String restOfWord = word.substring(1).toLowerCase();

	                result.append(firstChar).append(restOfWord).append(" ");
	            }
	        }
	        
	        return result.toString().trim();
	    }
	}