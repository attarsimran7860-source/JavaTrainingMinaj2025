package com.day3.lab2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

	 private static final Logger logger = LoggerFactory.getLogger(App.class);

	    public static void main(String[] args) {
	        String city = "Pune";
	        WheatherAggregator aggregator = new WheatherAggregator();

	        logger.info("Fetching weather report for {}...", city);

	        long startTime = System.currentTimeMillis();

	        try {
	           
	        	WheatherReport report = aggregator.getAggregatedWeather(city).join(); 
	            logger.info("Weather report successfully generated:\n{}", report);
	        } catch (java.util.concurrent.CompletionException e) {
	            
	            Throwable cause = e.getCause();
	            if (cause instanceof WheatherDataException) {
	               
	                logger.error("CRITICAL ERROR: No weather data could be retrieved for {}. Details: {}", city, cause.getMessage());
	               
	            } else {
	                logger.error("An unexpected error occurred while fetching weather for {}: {}", city, cause != null ? cause.getMessage() : e.getMessage(), cause);
	            }
	        } catch (Exception e) {
	            logger.error("An unexpected application error occurred: {}", e.getMessage(), e);
	        }

	        long endTime = System.currentTimeMillis();
	        logger.info("Total processing time: {} ms", (endTime - startTime));
	    }
	}
