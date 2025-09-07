package com.day2.lab1;

public class Guest implements Role{

	private static final String ROLE_NAME = "Guest";

    @Override
    public void accessResource(String resource) {
        if (canAccess(resource)) {
            System.out.println(ROLE_NAME + " access to '" + resource + "': GRANTED");
        } else {
            System.out.println(ROLE_NAME + " access to '" + resource + "': DENIED");
        }
    }

    @Override
    public boolean canAccess(String resource) {
        
        return "public-info".equalsIgnoreCase(resource);
    }

    @Override
    public String getRoleName() {
        return ROLE_NAME;
    }
}
