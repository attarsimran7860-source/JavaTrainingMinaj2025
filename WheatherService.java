package com.day3.lab2;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WheatherService {

	private static final Logger logger = LoggerFactory.getLogger(WheatherService.class);
    public final String serviceName;
    private final Random random = new Random();

    public WheatherService(String serviceName) {
        this.serviceName = serviceName;
    }

   
    public CompletableFuture<WheatherData> getWeatherDataAsync(String city) {
        return CompletableFuture.supplyAsync(() -> {
            long sleepTime = 500 + random.nextInt(1501); // 0.5ms
            boolean shouldFail = random.nextInt(100) < 30; // 30%

            try {
                logger.debug("Service '{}' for city '{}' is staring... (simulated delay: {}ms)", serviceName, city, sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Service '{}' for city '{}' was interrupted.", serviceName, city, e);
                throw new RuntimeException("Service " + serviceName + " was interrupted.", e);
            }

            if (shouldFail) {
                logger.error("Service '{}' for city '{}' simulated network failure.", serviceName, city);
                throw new RuntimeException("Simulated network failure for " + serviceName);
            }

            
            double temperature = 15.0 + random.nextDouble() * 15.0; // 15-30
            double humidity = 50.0 + random.nextDouble() * 40.0;    // 50-90
            double windSpeed = 5.0 + random.nextDouble() * 20.0;    // 5-25

            WheatherData data = new WheatherData(serviceName, temperature, humidity, windSpeed);
            logger.info("Service '{}' for city '{}' successfully returned data: {}", serviceName, city, data);
            return data;
        });
    }
}
