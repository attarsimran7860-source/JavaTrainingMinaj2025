package com.day7.lab2;

import java.util.Random;

public class Transaction implements Runnable {
	 private final Bank bank;
	    private final int fromAccountId;
	    private final int toAccountId;
	    private final double amount;
	    private final int transactionId;
	    private final String customName; 
	    private final long delayAfterFirstLockMillis; 
	    private final boolean reverseLockOrder; // To control the lock order

	    public Transaction(Bank bank, String customName, int fromAccountId, int toAccountId, double amount, boolean reverseLockOrder, long delayAfterFirstLockMillis) {
	        this.bank = bank;
	        this.customName = customName;
	        this.fromAccountId = fromAccountId;
	        this.toAccountId = toAccountId;
	        this.amount = amount;
	        this.transactionId = bank.getNextTransactionId();
	        this.reverseLockOrder = reverseLockOrder;
	        this.delayAfterFirstLockMillis = delayAfterFirstLockMillis;
	    }

	    @Override
	    public void run() {
	        
	        Thread.currentThread().setName(customName);

	        try {
	            Account account1 = bank.getAccount(fromAccountId);
	            Account account2 = bank.getAccount(toAccountId);

	            // Output matching the problem statement
	            System.out.println("[" + Thread.currentThread().getName() +
	                               "] Transferring " + String.format("%.0f", amount) +
	                               " from Account-" + fromAccountId + " to Account-" + toAccountId);
	            

	            if (reverseLockOrder) {
	                bank.performTransfer(account2, account1, amount, delayAfterFirstLockMillis);
	            } else { 
	                bank.performTransfer(account1, account2, amount, delayAfterFirstLockMillis);
	            }

	            System.out.println("[" + Thread.currentThread().getName() + "] Transaction completed successfully.");

	        } catch (InterruptedException e) {
	            System.out.println("[" + Thread.currentThread().getName() + "] Transaction aborted due to deadlock resolution.");
	            Thread.currentThread().interrupt(); // Restore the interrupted status
	        } catch (Exception e) {
	            System.err.println("[" + Thread.currentThread().getName() + "] T" + transactionId + " experienced an error: " + e.getMessage());
	        }
	    }
	}