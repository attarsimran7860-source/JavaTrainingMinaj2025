package com.day5.lab1;

public class DepartmentReportAccumulator {

	 private String departmentName; // Stored for the final report
	    private long employeeCount;
	    private double totalPerformanceScore; // Sum for calculating average
	    private int maxPerformanceScore;
	    private int minPerformanceScore;
	    private double totalYearsOfExperience; // Sum for calculating average

	    // Constructor for initial state
	    public DepartmentReportAccumulator(String departmentName) {
	        this.departmentName = departmentName;
	        this.employeeCount = 0;
	        this.totalPerformanceScore = 0;
	        this.maxPerformanceScore = Integer.MIN_VALUE; // Initialize for min/max tracking
	        this.minPerformanceScore = Integer.MAX_VALUE; // Initialize for min/max tracking
	        this.totalYearsOfExperience = 0;
	    }

	    // Method to accumulate an employee's data
	    public void accumulate(Employee employee) {
	        // Only accumulate if the employee's department matches this accumulator's department
	        // This is important if we're using this with a `groupingBy` collector before this.
	        // However, for this problem, we'll use a Map to store these, keyed by department,
	        // so this check isn't strictly necessary here but good practice for an accumulator.
	        if (this.departmentName == null || this.departmentName.equals(employee.getDepartment())) {
	            this.employeeCount++;
	            this.totalPerformanceScore += employee.getPerformanceScore();
	            this.maxPerformanceScore = Math.max(this.maxPerformanceScore, employee.getPerformanceScore());
	            this.minPerformanceScore = Math.min(this.minPerformanceScore, employee.getPerformanceScore());
	            this.totalYearsOfExperience += employee.getYearsOfExperience();
	        } else {
	             // This case should ideally not happen if used correctly within a Map<String, Accumulator>
	            System.err.println("Warning: Attempting to accumulate employee from wrong department.");
	        }
	    }

	    // Method to combine two accumulators (for parallel streams)
	    public DepartmentReportAccumulator combine(DepartmentReportAccumulator other) {
	        // Only combine if department names match (important for parallel processing)
	        if (!this.departmentName.equals(other.departmentName)) {
	            throw new IllegalArgumentException("Cannot combine accumulators for different departments.");
	        }

	        this.employeeCount += other.employeeCount;
	        this.totalPerformanceScore += other.totalPerformanceScore;
	        this.maxPerformanceScore = Math.max(this.maxPerformanceScore, other.maxPerformanceScore);
	        this.minPerformanceScore = Math.min(this.minPerformanceScore, other.minPerformanceScore);
	        this.totalYearsOfExperience += other.totalYearsOfExperience;
	        return this; // Return this instance (mutated)
	    }

	    // Method to finalize and create the DepartmentReport
	    public Department toDepartmentReport() {
	        if (employeeCount == 0) {
	            // Handle empty department case if ever relevant
	            return new Department(departmentName, 0, 0, 0, 0, 0);
	        }

	        double avgPerformance = totalPerformanceScore / employeeCount;
	        double avgExperience = totalYearsOfExperience / employeeCount;

	        // Note: min/max scores initialized to extreme values, so if employeeCount is 0,
	        // they'd still be those values. The 'if (employeeCount == 0)' handles this.
	        return new Department(departmentName, avgPerformance, maxPerformanceScore,
	                                    minPerformanceScore, avgExperience, employeeCount);
	    }
	}
