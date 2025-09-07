package com.day7.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogDataGenerator {

	 private static final String[] SAMPLE_URLS = {
	            "/home", "/products/123", "/products/456", "/checkout", "/cart",
	            "/search", "/profile", "/settings", "/about", "/contact"
	    };
	    private static final int MAX_USER_ID = 500; 
	    private static final int MAX_RESPONSE_TIME = 1000;

	    public static List<LogEntry> generateLogs(int count) {
	        List<LogEntry> logEntries = new ArrayList<>(count);
	        Random random = new Random();

	        for (int i = 0; i < count; i++) {
	            int userId = 1000 + random.nextInt(MAX_USER_ID); 
	            String url = SAMPLE_URLS[random.nextInt(SAMPLE_URLS.length)];
	            int responseTime = 50 + random.nextInt(MAX_RESPONSE_TIME - 50); 
	            logEntries.add(new LogEntry(userId, url, responseTime));
	        }
	        
	        return logEntries;
	    }
	}