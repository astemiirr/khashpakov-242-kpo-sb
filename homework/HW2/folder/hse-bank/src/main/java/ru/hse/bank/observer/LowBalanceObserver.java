package ru.hse.bank.observer;

import lombok.RequiredArgsConstructor;
import ru.hse.bank.domain.BankAccount;

@RequiredArgsConstructor
public class LowBalanceObserver implements BalanceObserver {
    private final double threshold;

    @Override
    public void update(BankAccount account, double oldBalance, double newBalance) {
        if (newBalance < threshold) {
            System.out.printf("ВНИМАНИЕ: На счете '%s' низкий баланс: %.2f%n",
                    account.getName(), newBalance);
        }
    }
}