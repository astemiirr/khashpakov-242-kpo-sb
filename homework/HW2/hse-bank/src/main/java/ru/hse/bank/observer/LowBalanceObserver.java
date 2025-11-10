package ru.hse.bank.observer;

import ru.hse.bank.domain.BankAccount;

public class LowBalanceObserver implements BalanceObserver {
    private final double threshold;
    
    public LowBalanceObserver(double threshold) {
        this.threshold = threshold;
    }
    
    @Override
    public void update(BankAccount account, double oldBalance, double newBalance) {
        if (newBalance < threshold) {
            System.out.printf("ВНИМАНИЕ: На счете '%s' низкий баланс: %.2f%n",
                            account.getName(), newBalance);
        }
    }
}