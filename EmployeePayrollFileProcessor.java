package com.day3.lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeePayrollFileProcessor {

	//private static final Logger logger = LoggerFactory.getLogger(EmployeePayrollFileProcessor.class);
	private static final Logger logger = LoggerFactory.getLogger(EmployeePayrollFileProcessor.class);

    private static final String INPUT_FILE_NAME = "employee.txt";
    private static final String OUTPUT_FILE_NAME = "processed_employees.txt";

    public static void main(String[] args) {
        logger.info("Employees Payroll processing started.");
        EmployeesProcessPayrollFile();
        logger.info("Employees Payroll processing finished.");
    }

    private static void EmployeesProcessPayrollFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
             BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME))) {
        	

            String line;
            int lineNumber = 0;

            writer.write("EmployeeID,Name,BasicSalary,Bonus\n");

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                	EmployeeRecord record = parseAndValidateRecord(line, lineNumber);
                    writer.write(record.toOutputFileFormat() + "\n");
                    logger.info("Line {}: Successfully processed employee ID {}", lineNumber, record.employeeId());
                } catch (InvalidException e) {
                    logger.error("Line {}: Invalid record encountered - {}", e.getLineNumber(), e.getMessage());
                } catch (Exception e) {
                    logger.error("Line {}: Unexpected error processing record - {}", lineNumber, e.getMessage(), e);
                }
            }

        } catch (IOException e) {
            logger.error("File I/O error: {}", e.getMessage(), e);
        } catch (SecurityException e) {
            logger.error("Security permission error: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred during payroll processing: {}", e.getMessage(), e);
        }
    }

    private static EmployeeRecord parseAndValidateRecord(String line, int lineNumber)
            throws InvalidException {
        String[] parts = line.split(",", -1);

        if (parts.length != 4) {
            throw new InvalidException("Incorrect number of fields.", lineNumber, "Expected 4 fields.");
        }

        String employeeIdStr = parts[0].trim();
        String name = parts[1].trim();
        String basicSalaryStr = parts[2].trim();
        String bonusStr = parts[3].trim();

        int employeeId;
        try {
            employeeId = Integer.parseInt(employeeIdStr);
            if (employeeId <= 0) {
                throw new InvalidException("EmployeeID must be positive.", lineNumber, "Invalid EmployeeID format/value: " + employeeIdStr);
            }
        } catch (NumberFormatException e) {
            throw new InvalidException("EmployeeID must be a numeric value.", lineNumber, "Invalid EmployeeID format: " + employeeIdStr);
        }

        if (name.isEmpty()) {
            throw new InvalidException("Name cannot be empty.", lineNumber, "Empty Name field.");
        }

        BigDecimal basicSalary;
        try {
            basicSalary = new BigDecimal(basicSalaryStr);
            if (basicSalary.compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvalidException("BasicSalary must be greater than zero.", lineNumber, "Invalid BasicSalary value: " + basicSalaryStr);
            }
        } catch (NumberFormatException e) {
            throw new InvalidException("BasicSalary must be a numeric value.", lineNumber, "Invalid BasicSalary format: " + basicSalaryStr);
        }

        BigDecimal bonus;
        try {
            bonus = new BigDecimal(bonusStr);
        } catch (NumberFormatException e) {
            throw new InvalidException("Bonus must be a numeric value.", lineNumber, "Invalid Bonus format: " + bonusStr);
        }

        BigDecimal netSalary = basicSalary.add(bonus);

        return new EmployeeRecord(employeeId, name, basicSalary, bonus, netSalary);
    }

    private record EmployeeRecord(
        int employeeId,
        String name,
        BigDecimal basicSalary,
        BigDecimal bonus,
        BigDecimal netSalary
    ) {
        public String toOutputFileFormat() {
            return String.format("%d,%s,%s,%s",
                    employeeId, name, basicSalary, bonus);
        }
    }
}
