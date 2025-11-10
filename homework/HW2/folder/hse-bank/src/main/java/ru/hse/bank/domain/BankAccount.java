package ru.hse.bank.domain;

import lombok.Data;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class BankAccount {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private final Long id; // исправить на Long
    private String name;
    private double balance;

    public BankAccount(String name, double initialBalance) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.name = name;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Сумма должна быть положительной");
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Сумма должна быть положительной");
        if (amount > balance) throw new IllegalArgumentException("Недостаточно средств");
        this.balance -= amount;
    }
}