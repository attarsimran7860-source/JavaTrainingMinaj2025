package com.day6.lab2;

import java.util.UUID;

public class RequestProcessor  implements Runnable {

    private final String requestName;

    public RequestProcessor(String requestName) {
        this.requestName = requestName;
    }

    @Override
    public void run() {
        String currentRequestId = null;
        try {
            
            currentRequestId = UUID.randomUUID().toString();
            RequestContext.setRequestId(currentRequestId);

            log("Starting request: " + requestName);

            
            try {
                Thread.sleep((long) (Math.random() * 200)); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log("Processing interrupted for request: " + requestName);
            }

            log("Processing request: " + requestName);

           
            try {
                Thread.sleep((long) (Math.random() * 200)); // Simulate more work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log("Processing interrupted for request: " + requestName);
            }

            log("Finished request: " + requestName);

        } finally {
          
            RequestContext.clear();
        }
    }

    private void log(String message) {
        String requestId = RequestContext.getRequestId();
        String threadName = Thread.currentThread().getName();
        System.out.printf("[%s] %s %s%n",
                threadName,
                requestId != null ? "Request ID: " + requestId + " - " : "",
                message);
    }
} 