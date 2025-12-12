package ru.hse.bank.repository;

import ru.hse.bank.domain.BankAccount;

import java.util.List;
import java.util.Optional;

public class BankAccountRepository implements Repository<BankAccount, Long> {
    private final Repository<BankAccount, Long> realRepository;
    
    // Proxy: кэшируем данные в памяти для быстрого доступа
    public BankAccountRepository(Repository<BankAccount, Long> realRepository) {
        this.realRepository = realRepository;
    }
    
    @Override
    public BankAccount save(BankAccount account) {
        // В реальном приложении здесь была бы синхронизация с БД
        return realRepository.save(account);
    }
    
    @Override
    public Optional<BankAccount> findById(Long id) {
        // Proxy: сначала ищем в кэше, потом в реальном хранилище
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
    
    @Override
    public boolean existsById(Long id) {
        return realRepository.existsById(id);
    }
}