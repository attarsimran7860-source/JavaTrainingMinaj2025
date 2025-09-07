package com.day7.lab2;
import java.util.Random;
public class SimulationMain {
	 private static final int NUM_ACCOUNTS = 2;
	    private static final double INITIAL_BALANCE = 1000.0;
	    private static final long DETECTOR_INTERVAL_MILLIS = 10; 
	    private static final int SIMULATION_DURATION_SECONDS = 3; 

	    public static void main(String[] args) throws InterruptedException {
	     

	        Bank bank = new Bank(NUM_ACCOUNTS, INITIAL_BALANCE);
	      
	       
	        DeadlockDetector detector = new DeadlockDetector(DETECTOR_INTERVAL_MILLIS);
	        Thread detectorThread = new Thread(detector, "DeadlockDetector");
	        detectorThread.setDaemon(true);
	        detectorThread.start();

	       
	        Transaction trans1 = new Transaction(bank, "Thread-1", 1, 2, 100.0, false, 50); 

	      
	        Transaction trans2 = new Transaction(bank, "Thread-2", 1, 2, 50.0, true, 50); 
	        
	        Thread thread1 = new Thread(trans1);
	        Thread thread2 = new Thread(trans2);

	        thread1.start();
	        Thread.sleep(10); 
	        thread2.start();

	        
	        Thread.sleep(SIMULATION_DURATION_SECONDS * 1000);

	        if (thread1.isAlive()) {
	            thread1.interrupt();
	        }
	        if (thread2.isAlive()) {
	            thread2.interrupt();
	        }

	        detector.shutdown();
	        detectorThread.join(100);

	       
	    }
	}