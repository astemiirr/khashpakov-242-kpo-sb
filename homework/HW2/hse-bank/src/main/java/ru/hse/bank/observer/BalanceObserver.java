package ru.hse.bank.observer;

import ru.hse.bank.domain.BankAccount;

public interface BalanceObserver {
    void update(BankAccount account, double oldBalance, double newBalance);
}