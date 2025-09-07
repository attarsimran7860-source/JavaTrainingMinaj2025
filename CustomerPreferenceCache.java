package com.day4.lab2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CustomerPreferenceCache {

	 private final ConcurrentHashMap<String, String> preferenceCache;

	    public CustomerPreferenceCache() {
	        this.preferenceCache = new ConcurrentHashMap<>();
	    }

	    
	    public String getPreference(String customerId) {
	        
	        return preferenceCache.computeIfAbsent(customerId, (id) -> {
	            
	            return simulateDatabaseFetch(id);
	        });
	    }

	    public void updatePreference(String customerId, String preference) {
	        
	        preferenceCache.put(customerId, preference);
	        System.out.println("Update: Updated preference for " + customerId);
	    }

	   
	    public void removePreference(String customerId) {
	       
	        preferenceCache.remove(customerId);
	        System.out.println("Remove: Removed preference for " + customerId);
	    }

	  
	    private String simulateDatabaseFetch(String customerId) {
	        try {
	           
	            TimeUnit.MILLISECONDS.sleep(200);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt(); 
	            System.err.println("Database fetch simulation interrupted.");
	        }
	        if ("CUST1".equals(customerId)) {
	            return "{\"theme\":\"dark\",\"lang\":\"en\",\"customer\":\"" + customerId + "\"}";
	        } else if ("CUST2".equals(customerId)) {
	            return "{\"theme\":\"light\",\"lang\":\"fr\",\"notifications\":\"true\",\"customer\":\"" + customerId + "\"}";
	        }
	        return "{\"theme\":\"default\",\"lang\":\"unset\",\"customer\":\"" + customerId + "\"}";
	    }

	    public static void main(String[] args) {
	        
	        CustomerPreferenceCache cache = new CustomerPreferenceCache();
	        String customerId = "CUST1";

	        System.out.println("  Action\t\t\t\t\tResult");
	        System.out.println("----------------------------------------------------------------------------------");

	        
	        System.out.print("First fetch:\t");
	        String preference1 = cache.getPreference(customerId);
	        System.out.println(preference1);

	        
	        System.out.print("Second fetch (cached):\t");
	        String preference2 = cache.getPreference(customerId);
	        System.out.println(preference2);

	      
	        String newPreference = "{\"theme\":\"light\",\"lang\":\"fr\",\"customer\":\"" + customerId + "\"}";
	        cache.updatePreference(customerId, newPreference);

	        
	        System.out.print("After update:\t");
	        String preferenceAfterUpdate = cache.getPreference(customerId); 
	        System.out.println(preferenceAfterUpdate);

	        
	        cache.removePreference(customerId);

	     
	        System.out.print("After removal, fetched again:\t");
	        String preferenceAfterRemoval = cache.getPreference(customerId);
	        System.out.println(preferenceAfterRemoval);

	    }
	}