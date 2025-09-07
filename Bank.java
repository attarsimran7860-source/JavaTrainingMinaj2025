package com.day7.lab2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank {
    private final Map<Integer, Account> accounts;
    private final AtomicInteger transactionCounter = new AtomicInteger(0);

    public Bank(int numberOfAccounts, double initialBalance) {
        this.accounts = new HashMap<>();
        for (int i = 1; i <= numberOfAccounts; i++) {
            accounts.put(i, new Account(i, initialBalance));
        }
    }

    public Account getAccount(int id) {
        return accounts.get(id);
    }

    public int getNextTransactionId() {
        return transactionCounter.incrementAndGet();
    }

   
    public void performTransfer(Account firstAccount, Account secondAccount, double amount, long delayAfterFirstLockMillis) throws InterruptedException {
        Object lock1 = firstAccount.getLock();
        Object lock2 = secondAccount.getLock();

        synchronized (lock1) {
          
            if (delayAfterFirstLockMillis > 0) {
                try {
                    Thread.sleep(delayAfterFirstLockMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }
            synchronized (lock2) {
               
                if (firstAccount.getBalance() >= amount) {
                    firstAccount.withdraw(amount);
                    secondAccount.deposit(amount);
                  
                    System.out.println("[" + Thread.currentThread().getName() + "] Transferred " + String.format("%.0f", amount) +
                                       " from Account-" + firstAccount.getId() + " to Account-" + secondAccount.getId());
                } else {
                    System.out.println("[" + Thread.currentThread().getName() + "] Insufficient funds in " + firstAccount.getId() +
                                       " to transfer " + String.format("%.0f", amount));
                }
            }
        }
    }

    public void printAllAccountBalances() {
        System.out.println("\n--- Current Bank Account Balances ---");
        accounts.values().forEach(System.out::println);
        System.out.println("-------------------------------------\n");
    }
}