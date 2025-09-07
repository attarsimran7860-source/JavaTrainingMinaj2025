package com.day6.lab2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainThread {

	  private static final int NUMBER_OF_THREADS = 5;
	    private static final int NUMBER_OF_REQUESTS = 15; // Increased for better demonstration

	    public static void main(String[] args) {
	       

	        // Create a fixed thread pool
	        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	        // Submit multiple requests to the thread pool
	        for (int i = 1; i <= NUMBER_OF_REQUESTS; i++) {
	            executor.submit(new RequestProcessor("Request-" + i));
	        }

	        // Shut down the executor and wait for all tasks to complete
	        executor.shutdown();
	        try {
	            // Wait for all tasks to finish or for a timeout
	            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
	                System.err.println("--------------------------------------------------");
	                System.err.println("Warning: Some tasks did not complete in time!");
	                executor.shutdownNow(); // Force shutdown if tasks are stuck
	            }
	        } catch (InterruptedException e) {
	            System.err.println("--------------------------------------------------");
	            System.err.println("Main thread interrupted while waiting for tasks to finish.");
	            executor.shutdownNow();
	            Thread.currentThread().interrupt();
	        }

	       
	    }
	}
