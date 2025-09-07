package com.day5.lab1;

import java.text.DecimalFormat;

public class Department {

	private String departmentName;
    private double averagePerformanceScore;
    private int maxPerformanceScore;
    private int minPerformanceScore;
    private double averageExperience;
    private long employeeCount;

    // Constructor to create a final report object
    public Department(String departmentName, double averagePerformanceScore, int maxPerformanceScore,
                            int minPerformanceScore, double averageExperience, long employeeCount) {
        this.departmentName = departmentName;
        this.averagePerformanceScore = averagePerformanceScore;
        this.maxPerformanceScore = maxPerformanceScore;
        this.minPerformanceScore = minPerformanceScore;
        this.averageExperience = averageExperience;
        this.employeeCount = employeeCount;
    }

    // Getters
    public String getDepartmentName() {
        return departmentName;
    }

    public double getAveragePerformanceScore() {
        return averagePerformanceScore;
    }

    public int getMaxPerformanceScore() {
        return maxPerformanceScore;
    }

    public int getMinPerformanceScore() {
        return minPerformanceScore;
    }

    public double getAverageExperience() {
        return averageExperience;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    @Override
    public String toString() {
        // Format doubles for readable output
        DecimalFormat df = new DecimalFormat("#.##");
        return "Department: " + departmentName + "\n" +
               " Avg Performance: " + df.format(averagePerformanceScore) + "\n" +
               " Max Performance: " + maxPerformanceScore + "\n" +
               " Min Performance: " + minPerformanceScore + "\n" +
               " Avg Experience: " + df.format(averageExperience) + "\n" +
               " Employee Count: " + employeeCount;
    }
}