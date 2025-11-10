package ru.hse.bank.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.hse.bank.domain.BankAccount;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BankAccountRepository implements ru.hse.bank.repository.Repository<BankAccount, Long> {
    private final BankAccountRepositoryImpl realRepository;

    @Override
    public BankAccount save(BankAccount account) {
        return realRepository.save(account);
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return realRepository.findById(id);
    }

    @Override
    public List<BankAccount> findAll() {
        return realRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        realRepository.delete(id);
    }
}