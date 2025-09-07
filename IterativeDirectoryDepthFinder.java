package com.day1.lab2;

import java.util.ArrayList;
import java.util.List;
public class IterativeDirectoryDepthFinder {


	    public static int findMaxDirectoryDepth(List<String> paths) {
	        if (paths == null || paths.isEmpty()) {
	            return 0; 
	        }

	        int maxDepth = 0;

	       
	        for (String path : paths) {
	            if (path == null || path.trim().isEmpty()) {
	                continue; 
	            }

	            
	            String normalizedPath = path;
	            if (normalizedPath.endsWith("/")) {
	                normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
	            }

	            
	            int currentDepth = 0;

	         
	            for (char c : normalizedPath.toCharArray()) {
	                if (c == '/') {
	                    currentDepth++;
	                }
	            }
	            if (currentDepth > maxDepth) {
	                maxDepth = currentDepth;
	            }
	        }

	        return maxDepth;
	    }

	    public static void main(String[] args) {
	        
	        List<String> paths1 = new ArrayList<>();
	        paths1.add("/home/user/docs");
	        paths1.add("/home/user/docs/reports");
	        paths1.add("/home/user/music");
	        System.out.println("Paths: " + paths1);
	        System.out.println("Max Depth: " + findMaxDirectoryDepth(paths1)); 
	    }
	}