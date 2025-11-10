package ru.hse.bank.domain;

import java.util.concurrent.atomic.AtomicLong;

public class BankAccount {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    
    private final Long id;
    private String name;
    private double balance;
    
    public BankAccount(String name, double initialBalance) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.name = name;
        this.balance = initialBalance;
    }
    
    // Геттеры и сеттеры
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма пополнения должна быть положительной");
        }
        this.balance += amount;
    }
    
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма списания должна быть положительной");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }
        this.balance -= amount;
    }
}