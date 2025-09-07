package com.day6.lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class TicketBooking {

	private static final int TOTAL_SEATS = 50;
    private static AtomicInteger availableSeatsCount = new AtomicInteger(TOTAL_SEATS);
    private static List<Integer> availableSeatNumbers;
    private static ReentrantLock bookingLock = new ReentrantLock();

    static {
        availableSeatNumbers = Collections.synchronizedList(new ArrayList<>());
        for (int i = 1; i <= TOTAL_SEATS; i++) {
            availableSeatNumbers.add(i);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Cinema Ticket Booking Simulation ---");
        System.out.println("Total Seats Available (initially): " + TOTAL_SEATS);
        System.out.println("----------------------------------------\n");

        // IMPORTANT: We need to create at least 10 users for the hardcoded scenario.
        // Creating more (e.g., 20) ensures all user IDs (1-10) are definitively instantiated.
        int numberOfUsers = 20;
        Thread[] users = new Thread[numberOfUsers];

        for (int i = 0; i < numberOfUsers; i++) {
            users[i] = new Thread(new UserSeats(i + 1), "User-" + (i + 1));
            users[i].start();
        }

        // Wait for all user threads to complete
        for (Thread user : users) {
            try {
                user.join();
            } catch (InterruptedException e) {
                System.err.println("Main thread interrupted while waiting for users: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n----------------------------------------");
        System.out.println("--- Booking Simulation Complete ---");
        int finalSeatsBooked = TOTAL_SEATS - availableSeatsCount.get();
        int finalRemainingSeats = availableSeatsCount.get();

        System.out.println("Total seats booked: " + finalSeatsBooked);
        System.out.println("Remaining seats: " + finalRemainingSeats);

        if (finalRemainingSeats == 0) {
            System.out.println("All tickets booked.");
        }
        System.out.println("----------------------------------------");
    }

    public static BookingResult bookTickets(int userId, int requestedSeats) {
        bookingLock.lock();
        try {
            int currentAvailablePhysicalSeats = availableSeatNumbers.size();
            List<Integer> seatsAssigned = new ArrayList<>();

            if (currentAvailablePhysicalSeats <= 0) {
                return new BookingResult(BookingStatus.FAILURE, 0, seatsAssigned);
            }

            int seatsToBook = Math.min(requestedSeats, currentAvailablePhysicalSeats);

            for (int i = 0; i < seatsToBook; i++) {
                if (!availableSeatNumbers.isEmpty()) {
                    seatsAssigned.add(availableSeatNumbers.remove(0));
                } else {
                    break;
                }
            }
            availableSeatsCount.set(availableSeatNumbers.size()); // Update atomic counter

            if (seatsAssigned.size() == requestedSeats) {
                return new BookingResult(BookingStatus.SUCCESS, seatsAssigned.size(), seatsAssigned);
            } else {
                return new BookingResult(BookingStatus.PARTIAL_SUCCESS, seatsAssigned.size(), seatsAssigned);
            }

        } finally {
            bookingLock.unlock();
        }
    }

    public enum BookingStatus {
        SUCCESS,
        PARTIAL_SUCCESS,
        FAILURE
    }

    public static class BookingResult {
        public final BookingStatus status;
        public final int seatsBooked;
        public final List<Integer> assignedSeatNumbers;

        public BookingResult(BookingStatus status, int seatsBooked, List<Integer> assignedSeatNumbers) {
            this.status = status;
            this.seatsBooked = seatsBooked;
            this.assignedSeatNumbers = assignedSeatNumbers;
        }
    }
}