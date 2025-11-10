package ru.hse.bank.domain;

import ru.hse.bank.observer.BalanceObserver;

import java.util.ArrayList;
import java.util.List;

public class ObservableBankAccount extends BankAccount {
    private final List<BalanceObserver> observers = new ArrayList<>();

    public ObservableBankAccount(String name, double initialBalance) {
        super(name, initialBalance);
    }

    public void addObserver(BalanceObserver observer) {
        observers.add(observer);
    }

    @Override
    public void deposit(double amount) {
        double oldBalance = getBalance();
        super.deposit(amount);
        notifyObservers(oldBalance, getBalance());
    }

    @Override
    public void withdraw(double amount) {
        double oldBalance = getBalance();
        super.withdraw(amount);
        notifyObservers(oldBalance, getBalance());
    }

    private void notifyObservers(double oldBalance, double newBalance) {
        for (BalanceObserver observer : observers) {
            observer.update(this, oldBalance, newBalance);
        }
    }
}