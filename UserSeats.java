package com.day6.lab1;

import java.util.List;
import java.util.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UserSeats  implements Runnable{

	  private final int userId;
	 
	    private static final Map<Integer, Integer> specificRequests = new HashMap<>();
	    static {
	        specificRequests.put(1, 4); 
	        specificRequests.put(2, 5); 
	        specificRequests.put(3, 3);
	        specificRequests.put(4, 5); 
	        specificRequests.put(5, 5);
	        specificRequests.put(6, 5); 
	        specificRequests.put(7, 5); 
	        specificRequests.put(8, 5); 
	        specificRequests.put(9, 12); 
	        specificRequests.put(10, 5); 
	    }
	    private final Random random;

	    public UserSeats(int userId) {
	        this.userId = userId;
	        this.random = new Random();
	    }

	    @Override
	    public void run() {
	        int requestedSeats;
	        // Get the hardcoded request for this user ID, if available.
	        // Otherwise, use a small random number, since seats will likely be gone anyway.
	        requestedSeats = specificRequests.getOrDefault(userId, random.nextInt(2) + 1); // For users > 10, request 1 or 2 seats

	        String threadName = Thread.currentThread().getName();

	        
	        try {
	            TimeUnit.MILLISECONDS.sleep(random.nextInt(50)); 
	        } catch (InterruptedException e) {
	            System.err.println(threadName + " was interrupted.");
	            Thread.currentThread().interrupt();
	            return;
	        }

	        TicketBooking.BookingResult result = TicketBooking.bookTickets(userId, requestedSeats);

	        
	        synchronized (System.out) {
	            System.out.print(threadName + " requested " + requestedSeats + " seats → ");
	            switch (result.status) {
	                case SUCCESS:
	                    System.out.printf("Booking successful (Seats: %s)\n", formatSeatNumbers(result.assignedSeatNumbers));
	                    break;
	                case PARTIAL_SUCCESS:
	                    System.out.printf("Only %d seat(s) available → Booking partially successful (Seat: %s)\n",
	                            result.seatsBooked, formatSeatNumbers(result.assignedSeatNumbers));
	                    break;
	                
	            }
	        }
	    }

	    private String formatSeatNumbers(List<Integer> seats) {
	        if (seats == null || seats.isEmpty()) {
	            return "None";
	        }
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < seats.size(); i++) {
	            sb.append(seats.get(i));
	            if (i < seats.size() - 1) {
	                sb.append(",");
	            }
	        }
	        return sb.toString();
	    }
	}