package com.day4.lab1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class CustomerProcessor {

	 private static final String INPUT_FILE = "customers.csv";
	    private static final String OUTPUT_FILE = "cleaned_customers.csv";
	    private static final String CSV_DELIMITER = ",";

	    public static void main(String[] args) {
	        System.out.println("Customer data processing started.");

	        List<Customer> customers = readCustomersFromCsv(INPUT_FILE);
	        System.out.println("Read " + customers.size() + " records from " + INPUT_FILE);

	        List<Customer> cleanedCustomers = deduplicateCustomers(customers);
	        System.out.println("After deduplication, " + cleanedCustomers.size() + " unique records remain.");

	        sortCustomers(cleanedCustomers);
	        System.out.println("Customers sorted.");

	        writeCustomersToCsv(cleanedCustomers, OUTPUT_FILE);
	        System.out.println("Cleaned and sorted records written to " + OUTPUT_FILE);

	        System.out.println("Customer data processing finished successfully.");
	    }

	  
	    private static List<Customer> readCustomersFromCsv(String fileName) {
	        List<Customer> customerList = new ArrayList<Customer>();
	        BufferedReader br = null;
	        String line = "";
	        try {
	            br = new BufferedReader(new FileReader(fileName));
	          
	            if ((line = br.readLine()) != null) {
	                
	            }

	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(CSV_DELIMITER);
	                if (data.length == 5) {
	                    try {
	                        int customerID = Integer.parseInt(data[0].trim());
	                        String name = data[1].trim();
	                        String email = data[2].trim();
	                        String phone = data[3].trim();
	                        String city = data[4].trim();
	                        customerList.add(new Customer(customerID, name, email, phone, city));
	                    } catch (NumberFormatException e) {
	                        System.err.println("Skipping malformed CustomerID: " + data[0] + " in line: " + line);
	                    }
	                } else {
	                    System.err.println("Skipping malformed line (incorrect column count): " + line);
	                }
	            }
	        } catch (FileNotFoundException e) {
	            System.err.println("Error: Input file not found: " + fileName);
	            e.printStackTrace();
	        } catch (IOException e) {
	            System.err.println("Error reading file: " + fileName);
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    System.err.println("Error closing BufferedReader: " + e.getMessage());
	                }
	            }
	        }
	        return customerList;
	    }

	    private static List<Customer> deduplicateCustomers(List<Customer> customers) {
	        
	        Map<String, Customer> uniqueCustomersByEmail = new HashMap<String, Customer>();
	        Map<String, Customer> uniqueCustomersByPhone = new HashMap<String, Customer>();

	        for (Customer customer : customers) {
	            String emailKey = customer.getEmail().toLowerCase();
	            String phoneKey = customer.getPhone();

	            
	            if (uniqueCustomersByEmail.containsKey(emailKey)) {
	                Customer existing = uniqueCustomersByEmail.get(emailKey);
	                if (customer.getCustomerID() < existing.getCustomerID()) {
	                    uniqueCustomersByEmail.put(emailKey, customer);
	                }
	            } else {
	                uniqueCustomersByEmail.put(emailKey, customer);
	            }

	            
	            if (uniqueCustomersByPhone.containsKey(phoneKey)) {
	                Customer existing = uniqueCustomersByPhone.get(phoneKey);
	                if (customer.getCustomerID() < existing.getCustomerID()) {
	                    uniqueCustomersByPhone.put(phoneKey, customer);
	                }
	            } else {
	                uniqueCustomersByPhone.put(phoneKey, customer);
	            }
	        }

	       
	        Set<Customer> finalUniqueCustomers = new HashSet<Customer>();
	        for (Customer c : uniqueCustomersByEmail.values()) {
	            finalUniqueCustomers.add(c);
	        }
	        for (Customer c : uniqueCustomersByPhone.values()) {
	           
	            boolean foundExisting = false;
	            for(Customer existingFinal : finalUniqueCustomers){
	                if(existingFinal.getEmail().equalsIgnoreCase(c.getEmail()) || existingFinal.getPhone().equals(c.getPhone())){
	                    foundExisting = true;
	                    if(c.getCustomerID() < existingFinal.getCustomerID()){
	                        finalUniqueCustomers.remove(existingFinal); // 古いものを削除
	                        finalUniqueCustomers.add(c); // 新しいものを追加
	                    }
	                    break;
	                }
	            }
	            if(!foundExisting){
	                finalUniqueCustomers.add(c);
	            }
	        }


	        Map<String, Customer> uniqueCustomers = new HashMap<String, Customer>();

	        for (Customer currentCustomer : customers) {
	            String emailLower = currentCustomer.getEmail().toLowerCase();
	            String phone = currentCustomer.getPhone();

	            boolean handled = false;

	           
	            for (Map.Entry<String, Customer> entry : uniqueCustomers.entrySet()) {
	                Customer existingCustomer = entry.getValue();
	                
	                if (existingCustomer.getEmail().equalsIgnoreCase(emailLower) || existingCustomer.getPhone().equals(phone)) {
	                   
	                    if (currentCustomer.getCustomerID() < existingCustomer.getCustomerID()) {
	                   
	                        handled = true;
	                        break; 
	                    } else {
	                        
	                        handled = true;
	                        break;
	                    }
	                }
	            }

	            if (!handled) {
	               
	                uniqueCustomers.put(emailLower + "_" + phone, currentCustomer); 
	            }
	        }

	        
	        List<Customer> resultList = new ArrayList<Customer>();

	        for (Customer newCustomer : customers) {
	            boolean isDuplicate = false;
	            for (int i = 0; i < resultList.size(); i++) {
	                Customer existingCustomer = resultList.get(i);
	              
	                if (newCustomer.getEmail().equalsIgnoreCase(existingCustomer.getEmail()) || newCustomer.getPhone().equals(existingCustomer.getPhone())) {
	                    isDuplicate = true;
	                    
	                    if (newCustomer.getCustomerID() < existingCustomer.getCustomerID()) {
	                        resultList.set(i, newCustomer); 
	                    }
	                    
	                    break;
	                }
	            }
	            if (!isDuplicate) {
	               
	                resultList.add(newCustomer);
	            }
	        }
	       
	        Map<String, Customer> emailToBestCustomer = new HashMap<String, Customer>();
	        Map<String, Customer> phoneToBestCustomer = new HashMap<String, Customer>();

	        for (Customer c : customers) {
	            String emailLower = c.getEmail().toLowerCase();
	            String phone = c.getPhone();

	            
	            if (!emailToBestCustomer.containsKey(emailLower) || c.getCustomerID() < emailToBestCustomer.get(emailLower).getCustomerID()) {
	                emailToBestCustomer.put(emailLower, c);
	            }

	            
	            if (!phoneToBestCustomer.containsKey(phone) || c.getCustomerID() < phoneToBestCustomer.get(phone).getCustomerID()) {
	                phoneToBestCustomer.put(phone, c);
	            }
	        }

	   
	        Set<Customer> finalCustomersSet = new HashSet<Customer>();

	        
	        for (Customer emailBest : emailToBestCustomer.values()) {
	            finalCustomersSet.add(emailBest);
	        }

	        
	        for (Customer phoneBest : phoneToBestCustomer.values()) {
	            boolean foundConflictAndResolved = false;
	            
	            List<Customer> tempFinalCustomersList = new ArrayList<Customer>(finalCustomersSet);
	            for (int i = 0; i < tempFinalCustomersList.size(); i++) {
	                Customer existingInFinal = tempFinalCustomersList.get(i);
	                if (phoneBest.getEmail().equalsIgnoreCase(existingInFinal.getEmail()) || phoneBest.getPhone().equals(existingInFinal.getPhone())) {
	                    
	                    foundConflictAndResolved = true;
	                    if (phoneBest.getCustomerID() < existingInFinal.getCustomerID()) {
	                        tempFinalCustomersList.set(i, phoneBest); 
	                    }
	                    break;
	                }
	            }
	            if (!foundConflictAndResolved) {
	                tempFinalCustomersList.add(phoneBest); 
	            }
	            finalCustomersSet = new HashSet<Customer>(tempFinalCustomersList); 
	        }
	        // System.out.println("DEBUG: Final customers after map merge: " + finalCustomersSet.size());
	        return new ArrayList<Customer>(finalCustomersSet);
	    }


	    private static void sortCustomers(List<Customer> customers) {
	        Collections.sort(customers, new Customer.CustomerComparator());
	    }

	    private static void writeCustomersToCsv(List<Customer> customers, String fileName) {
	        BufferedWriter bw = null;
	        try {
	            bw = new BufferedWriter(new FileWriter(fileName));
	            
	            bw.write("CustomerID,Name,Email,Phone,City");
	            bw.newLine();

	            for (Customer customer : customers) {
	                bw.write(customer.toString());
	                bw.newLine();
	            }
	        } catch (IOException e) {
	            System.err.println("Error writing to file: " + fileName);
	            e.printStackTrace();
	        } finally {
	            if (bw != null) {
	                try {
	                    bw.close();
	                } catch (IOException e) {
	                    System.err.println("Error closing BufferedWriter: " + e.getMessage());
	                }
	            }
	        }
	    }
	}