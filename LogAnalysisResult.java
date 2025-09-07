package com.day7.lab1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class LogAnalysisResult {

	  private Map<Integer, Long> userRequestCounts;
	    private Map<String, Long> urlVisitCounts;
	    private long totalResponseTime;
	    private long totalProcessedLogs;

	    public LogAnalysisResult() {
	        this.userRequestCounts = new HashMap<>();
	        this.urlVisitCounts = new HashMap<>();
	        this.totalResponseTime = 0;
	        this.totalProcessedLogs = 0;
	    }

	    // Constructor for initial population
	    public LogAnalysisResult(Map<Integer, Long> userRequestCounts, Map<String, Long> urlVisitCounts,
	                             long totalResponseTime, long totalProcessedLogs) {
	        this.userRequestCounts = userRequestCounts;
	        this.urlVisitCounts = urlVisitCounts;
	        this.totalResponseTime = totalResponseTime;
	        this.totalProcessedLogs = totalProcessedLogs;
	    }

	    public void addUserRequest(int userId) {
	        userRequestCounts.merge(userId, 1L, Long::sum);
	    }

	    public void addUrlVisit(String url) {
	        urlVisitCounts.merge(url, 1L, Long::sum);
	    }

	    public void addResponseTime(int responseTime) {
	        this.totalResponseTime += responseTime;
	        this.totalProcessedLogs++;
	    }

	    public Map<Integer, Long> getUserRequestCounts() {
	        return userRequestCounts;
	    }

	    public Map<String, Long> getUrlVisitCounts() {
	        return urlVisitCounts;
	    }

	    public long getTotalResponseTime() {
	        return totalResponseTime;
	    }

	    public long getTotalProcessedLogs() {
	        return totalProcessedLogs;
	    }

	    // Method to merge another result into this one
	    public void merge(LogAnalysisResult other) {
	        other.userRequestCounts.forEach((userId, count) ->
	                this.userRequestCounts.merge(userId, count, Long::sum));

	        other.urlVisitCounts.forEach((url, count) ->
	                this.urlVisitCounts.merge(url, count, Long::sum));

	        this.totalResponseTime += other.totalResponseTime;
	        this.totalProcessedLogs += other.totalProcessedLogs;
	    }

	    public void printResults() {
	        
	        System.out.println("Top 5 URLs: ");
	        urlVisitCounts.entrySet().stream()
	                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
	                .limit(5)
	                .forEach(entry -> System.out.println(entry.getKey() + " -> " + entry.getValue() + " hits"));

	        System.out.println("\nUser Request Counts :");
	        userRequestCounts.entrySet().stream()
	                .sorted(Map.Entry.comparingByKey()) // Sort by userId
	                .limit(10) // Limit to first 10 users for display
	                .forEach(entry -> System.out.println("userId=" + entry.getKey() + " -> " + entry.getValue() + " requests"));
	        if (userRequestCounts.size() > 10) {
	         
	        }


	        if (totalProcessedLogs > 0) {
	            double avgResponseTime = (double) totalResponseTime / totalProcessedLogs;
	            System.out.printf("\nAverage Response Time: %.2f ms%n", avgResponseTime);
	        } else {
	            System.out.println("\nNo logs processed for average response time calculation.");
	        }
	        
	    }
	}