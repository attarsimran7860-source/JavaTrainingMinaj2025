package com.day1.lab2;

import java.util.Arrays;

public class RecursiveStopVIPNumber {

	private static final int VIP_ID = 999;

   
    public static void processCustomerQueue(int[] customerIDs) {
        if (customerIDs == null || customerIDs.length == 0) {
            System.out.println("Queue is empty or null. No customers to process.");
            return;
        }
        System.out.println("Starting customer queue processing...");
        processCustomerRecursive(customerIDs, 0); 
        System.out.println("Customer queue processing finished.");
    }

  
    private static void processCustomerRecursive(int[] customerIDs, int currentIndex) {
        
        if (currentIndex >= customerIDs.length) {
            return; 
        }

        int currentCustomerID = customerIDs[currentIndex];

        
        if (currentCustomerID == VIP_ID) {
            System.out.println("Customer ID " + currentCustomerID + " (VIP) encountered. Stopping further processing.");
            return; 
        }

      
        System.out.println("Processing customer ID: " + currentCustomerID);

        
        processCustomerRecursive(customerIDs, currentIndex + 1);
    }

    public static void main(String[] args) {
       
        int[] queue1 = {101, 102, VIP_ID, 103, 104};
        System.out.println("--- Test Case 1: VIP in the middle ---");
        System.out.println("Queue: " + Arrays.toString(queue1));
        processCustomerQueue(queue1);
        System.out.println("\n");

       
        int[] queue2 = {VIP_ID, 201, 202};
        System.out.println("--- Test Case 2: VIP at the beginning ---");
        System.out.println("Queue: " + Arrays.toString(queue2));
        processCustomerQueue(queue2);
        System.out.println("\n");

        
        int[] queue3 = {301, 302, VIP_ID};
        System.out.println("--- Test Case 3: VIP at the end ---");
        System.out.println("Queue: " + Arrays.toString(queue3));
        processCustomerQueue(queue3);
        System.out.println("\n");

       
        int[] queue4 = {401, 402, 403, 404};
        System.out.println("--- Test Case 4: No VIP ---");
        System.out.println("Queue: " + Arrays.toString(queue4));
        processCustomerQueue(queue4);
        System.out.println("\n");

       
        int[] queue5 = {};
        System.out.println("--- Test Case 5: Empty queue ---");
        System.out.println("Queue: " + Arrays.toString(queue5));
        processCustomerQueue(queue5);
        System.out.println("\n");

        
        int[] queue6 = null;
        System.out.println("--- Test Case 6: Null queue ---");
        System.out.println("Queue: " + Arrays.toString(queue6));
        processCustomerQueue(queue6);
        System.out.println("\n");
    }
}
