package com.day3.lab2;

public class WheatherReport {

	private final double averageTemperature;
    private final double averageHumidity;
    private final double averageWindSpeed;
    private final int successfulSources;
    private final int totalSources;

    public WheatherReport(double averageTemperature, double averageHumidity, double averageWindSpeed,
                         int successfulSources, int totalSources) {
        this.averageTemperature = averageTemperature;
        this.averageHumidity = averageHumidity;
        this.averageWindSpeed = averageWindSpeed;
        this.successfulSources = successfulSources;
        this.totalSources = totalSources;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public double getAverageHumidity() {
        return averageHumidity;
    }

    public double getAverageWindSpeed() {
        return averageWindSpeed;
    }

    public int getSuccessfulSources() {
        return successfulSources;
    }

    public int getTotalSources() {
        return totalSources;
    }

    @Override
    public String toString() {
        return "--- Weather Report ---\n" +
               String.format("Average Temperature: %.2f°C\n", averageTemperature) +
               String.format("Average Humidity: %.2f%%\n", averageHumidity) +
               String.format("Average Wind Speed: %.2f km/h\n", averageWindSpeed) +
               String.format("Data from %d/%d sources successful.\n", successfulSources, totalSources);
    }
}
