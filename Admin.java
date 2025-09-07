package com.day2.lab1;

public class Admin implements Role{

	 private static final String ROLE_NAME = "Admin";

	    @Override
	    public void accessResource(String resource) {
	        if (canAccess(resource)) {
	            System.out.println(ROLE_NAME + " access to '" + resource + "': GRANTED");
	        } else {
	            
	            System.out.println(ROLE_NAME + " access to '" + resource + "': DENIED (Should not happen for Admin)");
	        }
	    }

	    @Override
	    public boolean canAccess(String resource) {
	        
	        return true;
	    }

	    @Override
	    public String getRoleName() {
	        return ROLE_NAME;
	    }
	}
