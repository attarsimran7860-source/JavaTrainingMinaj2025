package com.day5.lab1;

public class Employee {
	private String name;
    private String department;
    private int performanceScore; // range 0 - 100
    private int yearsOfExperience;

    public Employee(String name, String department, int performanceScore, int yearsOfExperience) {
        this.name = name;
        this.department = department;
        this.performanceScore = performanceScore;
        this.yearsOfExperience = yearsOfExperience;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getPerformanceScore() {
        return performanceScore;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    @Override
    public String toString() {
        return "Employee{" +
               "name='" + name + '\'' +
               ", department='" + department + '\'' +
               ", performanceScore=" + performanceScore +
               ", yearsOfExperience=" + yearsOfExperience +
               '}';
    }
}