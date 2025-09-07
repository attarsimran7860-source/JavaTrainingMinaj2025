package com.day3.lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WheatherAggregator {

	 private static final Logger logger = LoggerFactory.getLogger(WheatherAggregator.class);
	    private final List<WheatherService> services;

	    public WheatherAggregator() {
	        this.services = Arrays.asList(
	                new WheatherService("ServiceA"),
	                new WheatherService("ServiceB"),
	                new WheatherService("ServiceC")
	        );
	    }

	   
	    public CompletableFuture<WheatherReport> getAggregatedWeather(String city) {
	        List<CompletableFuture<WheatherData>> futures = services.stream()
	                .map(service -> service.getWeatherDataAsync(city)
	                        .exceptionally(ex -> {
	                            logger.error("Failed to get weather data from {}: {}", service.serviceName, ex.getMessage());
	                            return null;
	                        }))
	                .collect(Collectors.toList());

	      
	        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

	        return allOf.thenApply(v -> {
	            List<WheatherData> successfulResults = new ArrayList<>();
	            for (CompletableFuture<WheatherData> future : futures) {
	                if (!future.isCompletedExceptionally()) {
	                    try {
	                    	WheatherData data = future.getNow(null); 
	                        if (data != null) {
	                            successfulResults.add(data);
	                        }
	                    } catch (Exception e) {
	                        
	                        logger.error("Unexpected error retrieving result from future: {}", e.getMessage());
	                    }
	                }
	            }

	            int totalSources = services.size();
	            int successfulSources = successfulResults.size();

	            if (successfulSources == 0) {
	                logger.error("All weather services failed to provide data for '{}'.", city);
	                throw new RuntimeException(new WheatherDataException("No weather data available from any service."));
	            } else if (successfulSources < totalSources) {
	                logger.warn("Only {}/{} weather services returned successful data for '{}'.", successfulSources, totalSources, city);
	            } else {
	                logger.info("All {} weather services returned successful data for '{}'.", successfulSources, city);
	            }

	            double totalTemperature = successfulResults.stream().mapToDouble(WheatherData::getTemperature).sum();
	            double totalHumidity = successfulResults.stream().mapToDouble(WheatherData::getHumidity).sum();
	            double totalWindSpeed = successfulResults.stream().mapToDouble(WheatherData::getWindSpeed).sum();

	            double avgTemp = totalTemperature / successfulSources;
	            double avgHum = totalHumidity / successfulSources;
	            double avgWind = totalWindSpeed / successfulSources;

	            return new WheatherReport(avgTemp, avgHum, avgWind, successfulSources, totalSources);
	        }).exceptionally(ex -> {
	            Throwable cause = ex.getCause();
	            if (cause instanceof WheatherDataException) {
	                
	                throw new RuntimeException(cause); 
	            } else {
	                logger.error("An unexpected error occurred during weather aggregation: {}", ex.getMessage());
	                throw new RuntimeException("Failed to aggregate weather data.", ex);
	            }
	        });
	    }
	}