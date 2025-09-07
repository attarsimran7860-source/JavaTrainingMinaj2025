package com.day3.lab2;

public class WheatherData {

	private final String source;
    private final double temperature;
    private final double humidity;
    private final double windSpeed;

    public WheatherData(String source, double temperature, double humidity, double windSpeed) {
        this.source = source;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public String getSource() {
        return source;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
               "source='" + source + '\'' +
               ", temperature=" + temperature +
               ", humidity=" + humidity +
               ", windSpeed=" + windSpeed +
               '}';
    }
}
