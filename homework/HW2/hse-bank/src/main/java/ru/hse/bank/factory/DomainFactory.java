package ru.hse.bank.factory;

import ru.hse.bank.domain.BankAccount;
import ru.hse.bank.domain.ObservableBankAccount;
import ru.hse.bank.domain.Category;
import ru.hse.bank.domain.CategoryType;
import ru.hse.bank.domain.Operation;
import ru.hse.bank.domain.OperationType;

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
    
    public Category createCategory(CategoryType type, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название категории не может быть пустым");
        }
        return new Category(type, name);
    }
    
    public Operation createOperation(OperationType type, Long bankAccountId, 
                                   double amount, String description, Long categoryId) {
        if (bankAccountId == null) {
            throw new IllegalArgumentException("ID счета не может быть null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма операции должна быть положительной");
        }
        if (categoryId == null) {
            throw new IllegalArgumentException("ID категории не может быть null");
        }
        
        return new Operation(type, bankAccountId, amount, description, categoryId);
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
