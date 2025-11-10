package ru.hse.bank.factory;

import org.springframework.stereotype.Component;
import ru.hse.bank.domain.BankAccount;
import ru.hse.bank.domain.ObservableBankAccount;

@Component
public class DomainFactory {

    public BankAccount createBankAccount(String name, double initialBalance) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название счета не может быть пустым");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        }
        return new BankAccount(name, initialBalance);
    }

    public BankAccount createObservableBankAccount(String name, double initialBalance) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название счета не может быть пустым");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        }
        return new ObservableBankAccount(name, initialBalance);
    }
}