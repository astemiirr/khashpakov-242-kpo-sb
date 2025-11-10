package ru.hse.bank.di;

import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.repository.BankAccountRepository;
import ru.hse.bank.repository.BankAccountRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Map<Class<?>, Class<?>> implementations = new HashMap<>();
    
    public DIContainer() {
        setupImplementations();
        initializeBeans();
    }
    
    private void setupImplementations() {
        implementations.put(DomainFactory.class, DomainFactory.class);
        implementations.put(BankAccountRepository.class, BankAccountRepository.class);
        implementations.put(BankAccountFacade.class, BankAccountFacade.class);
    }
    
    private void initializeBeans() {
        // Создаем бины в правильном порядке зависимостей
        DomainFactory factory = new DomainFactory();
        BankAccountRepositoryImpl realRepository = new BankAccountRepositoryImpl();
        BankAccountRepository repository = new BankAccountRepository(realRepository);
        BankAccountFacade facade = new BankAccountFacade(repository, factory);
        
        beans.put(DomainFactory.class, factory);
        beans.put(BankAccountRepository.class, repository);
        beans.put(BankAccountFacade.class, facade);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> beanClass) {
        Object bean = beans.get(beanClass);
        if (bean == null) {
            throw new IllegalArgumentException("Bean not found: " + beanClass.getName());
        }
        return (T) bean;
    }
}
