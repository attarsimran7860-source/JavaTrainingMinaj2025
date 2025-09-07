package com.day1.lab2;

import java.util.*;

public class RecursiveDirectoryDepthFinder {

	  
    public static int findDepthRecursive(String path) {
        
        if (path == null || path.isEmpty()) {
            return 0;
        }
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (path.equals("/")) {
            return 0;
        }
        int lastSlashIndex = path.lastIndexOf('/');
        if (lastSlashIndex == -1) {
            return 1;
        }
        return 1 + findDepthRecursive(path.substring(0, lastSlashIndex));
    }


   public static int findMaxDepthInSet(List<String> paths) {
        if (paths == null || paths.isEmpty()) {
            return 0;
        }

        int maxDepth = 0;
        for (String path : paths) {
            int currentDepth = findDepthRecursive(path); 
            if (currentDepth > maxDepth) {
                maxDepth = currentDepth;
            }
        }
        return maxDepth;
    }

    public static void main(String[] args) {
        List<String> folderPaths = Arrays.asList(
        		"/home/user/docs", "/home/user/docs/reports", "/home/user/music"
        );

        System.out.println("Folder paths: " + folderPaths);
        int maxDepth = findMaxDepthInSet(folderPaths);
        System.out.println("Maximum depth found: " + maxDepth);

       
    }
}
