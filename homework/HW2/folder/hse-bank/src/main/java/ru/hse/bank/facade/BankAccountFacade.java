package ru.hse.bank.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hse.bank.domain.BankAccount;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.repository.BankAccountRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountFacade {
    private final BankAccountRepository repository;
    private final DomainFactory factory;

    public BankAccount createAccount(String name, double initialBalance) {
        BankAccount account = factory.createBankAccount(name, initialBalance);
        return repository.save(account);
    }

    public Optional<BankAccount> getAccount(Long id) {
        return repository.findById(id);
    }

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    public void deposit(Long accountId, double amount) {
        Optional<BankAccount> accountOpt = repository.findById(accountId);
        if (accountOpt.isPresent()) {
            BankAccount account = accountOpt.get();
            account.deposit(amount);
            repository.save(account);
        }
    }

    public void withdraw(Long accountId, double amount) {
        Optional<BankAccount> accountOpt = repository.findById(accountId);
        if (accountOpt.isPresent()) {
            BankAccount account = accountOpt.get();
            account.withdraw(amount);
            repository.save(account);
        }
    }
}