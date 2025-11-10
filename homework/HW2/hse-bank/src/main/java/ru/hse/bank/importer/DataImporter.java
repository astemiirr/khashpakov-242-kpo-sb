package ru.hse.bank.importer;

import ru.hse.bank.domain.BankAccount;
import ru.hse.bank.domain.Category;
import ru.hse.bank.domain.Operation;

import java.util.List;

public abstract class DataImporter {
    // Шаблонный метод
    public final void importData(String filePath) {
        String content = readFile(filePath);
        List<BankAccount> accounts = parseAccounts(content);
        List<Category> categories = parseCategories(content);
        List<Operation> operations = parseOperations(content);
        
        saveAccounts(accounts);
        saveCategories(categories);
        saveOperations(operations);
        
        System.out.println("Импорт завершен: " + accounts.size() + " счетов, " +
                          categories.size() + " категорий, " + operations.size() + " операций");
    }
    
    protected abstract String readFile(String filePath);
    protected abstract List<BankAccount> parseAccounts(String content);
    protected abstract List<Category> parseCategories(String content);
    protected abstract List<Operation> parseOperations(String content);
    
    protected void saveAccounts(List<BankAccount> accounts) {
        // В реальном приложении здесь должно быть сохранение в репозиторий
        System.out.println("Сохранение счетов...");
    }
    
    protected void saveCategories(List<Category> categories) {
        System.out.println("Сохранение категорий...");
    }
    
    protected void saveOperations(List<Operation> operations) {
        System.out.println("Сохранение операций...");
    }
}