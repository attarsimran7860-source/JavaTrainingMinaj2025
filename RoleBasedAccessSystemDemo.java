package com.day2.lab1;

public class RoleBasedAccessSystemDemo {

    public static void main(String[] args) {
        AccessSystem system = new AccessSystem();

        System.out.println("--- Manager Role ---");
        Role manager = new Manager();
        system.requestAccess(manager, "reports");
        system.requestAccess(manager, "team-data");
        system.requestAccess(manager, "confidential");
        system.requestAccess(manager, "public-info"); 
        system.requestAccess(manager, "payroll");

        System.out.println("\n--- Admin Role ---");
        Role admin = new Admin();
        system.requestAccess(admin, "reports");
        system.requestAccess(admin, "confidential");
        system.requestAccess(admin, "any-new-resource"); 

        System.out.println("\n--- Employee Role ---");
        Role employee = new Employee();
        system.requestAccess(employee, "team-data");
        system.requestAccess(employee, "reports");
        system.requestAccess(employee, "public-info");

        System.out.println("\n--- Guest Role ---");
        Role guest = new Guest();
        system.requestAccess(guest, "public-info");
        system.requestAccess(guest, "team-data");
        system.requestAccess(guest, "reports");

        
        // Role intern = new Intern();
        // system.requestAccess(intern, "public-info");
        // system.requestAccess(intern, "training-materials");
        // system.requestAccess(intern, "team-data");
    }
}
