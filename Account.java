package com.day7.lab2;

public class Account {

	private final int id;
    private double balance;
    private final Object lock = new Object(); 

    public Account(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    public Object getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "Account-" + id + " (Balance: " + String.format("%.2f", balance) + ")";
    }
}