package ru.hse.bank.repository;

import org.springframework.stereotype.Component;
import ru.hse.bank.domain.BankAccount;

@Component
public class BankAccountRepositoryImpl extends InMemoryRepository<BankAccount, Long> {
    @Override
    protected Long getId(BankAccount entity) {
        return entity.getId();
    }
}