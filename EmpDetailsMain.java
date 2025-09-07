package com.day5.lab1;

import java.util.*;

public class EmpDetailsMain {

	public static void main(String[] args) {
        List<Employee> employees = List.of(
            new Employee("Alice", "Engineering", 85, 4),
            new Employee("Bob", "Engineering", 90, 5),
            new Employee("Charlie", "HR", 70, 2),
            new Employee("Diana", "Engineering", 60, 1),
            new Employee("Eve", "HR", 95, 6),
            new Employee("Frank", "Sales", 80, 3)
        );

        System.out.println("--- Generating Report (Sequential Stream) ---");
        Map<String, Department> sequentialReport = employees.stream()
            .collect(DepartmentReportCollector.departmentReportCollector());

        sequentialReport.values().forEach(System.out::println);

        System.out.println("\n--- Generating Report (Parallel Stream) ---");
        Map<String, Department> parallelReport = employees.parallelStream()
            .collect(DepartmentReportCollector.departmentReportCollector());

        parallelReport.values().forEach(System.out::println);

      
        if (sequentialReport.equals(parallelReport)) {
            System.out.println("Sequential and Parallel reports are identical.");
        } 
    }
}