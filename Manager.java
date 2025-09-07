package com.day2.lab1;

public class Manager implements Role{

	private static final String ROLE_NAME = "Manager";

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
       
        switch (resource.toLowerCase()) {
            case "reports":
            case "team-data":
                return true;
            case "confidential":
                return false; 
            default:
                return false; 
        }
    }

    @Override
    public String getRoleName() {
        return ROLE_NAME;
    }
}
