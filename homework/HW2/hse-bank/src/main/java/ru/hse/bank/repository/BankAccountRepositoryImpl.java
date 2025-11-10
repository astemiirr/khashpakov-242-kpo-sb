package ru.hse.bank.repository;

import ru.hse.bank.domain.BankAccount;

public class BankAccountRepositoryImpl extends InMemoryRepository<BankAccount, Long> {
    @Override
    protected Long getId(BankAccount entity) {
        return entity.getId();
    }
}