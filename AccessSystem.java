package com.day2.lab1;

public class AccessSystem {

	public void requestAccess(Role role, String resource) {
        System.out.print("Request by " + role.getRoleName() + " for '" + resource + "': ");
        role.accessResource(resource);
    }
}
