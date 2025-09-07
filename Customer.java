package com.day4.lab1;

import java.util.Comparator;
import java.util.Objects;

public class Customer {

	 private int customerID;
	    private String name;
	    private String email;
	    private String phone;
	    private String city;

	    public Customer(int customerID, String name, String email, String phone, String city) {
	        this.customerID = customerID;
	        this.name = name;
	        this.email = email;
	        this.phone = phone;
	        this.city = city;
	    }

	    
	    public int getCustomerID() {
	        return customerID;
	    }

	    public String getName() {
	        return name;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public String getPhone() {
	        return phone;
	    }

	    public String getCity() {
	        return city;
	    }

	    @Override
	    public String toString() {
	        return customerID + "," + name + "," + email + "," + phone + "," + city;
	    }

	   
	    public static class CustomerComparator implements Comparator<Customer> {
	        public int compare(Customer c1, Customer c2) {
	            
	            int cityComparison = c1.getCity().compareTo(c2.getCity());
	            if (cityComparison != 0) {
	                return cityComparison;
	            }
	            
	            return c1.getName().compareTo(c2.getName());
	        }
	    }
	}