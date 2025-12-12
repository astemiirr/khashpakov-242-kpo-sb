package ru.hse.bank.importer;

import ru.hse.bank.domain.BankAccount;
import ru.hse.bank.domain.Category;
import ru.hse.bank.domain.Operation;

import java.util.List;

public class JsonDataImporter extends DataImporter {

    @Override
    protected String readFile(String filePath) {
        System.out.println("Чтение JSON файла: " + filePath);
        // В реальном приложении здесь был бы код для чтения файла
        return "{\"accounts\": [], \"categories\": [], \"operations\": []}";
    }

    @Override
    protected List<BankAccount> parseAccounts(String content) {
        System.out.println("Парсинг JSON данных счетов...");
        // ... здесь был бы парсинг JSON
        // Например, с использованием Jackson ObjectMapper
        return List.of();
    }

    @Override
    protected List<Category> parseCategories(String content) {
        System.out.println("Парсинг JSON данных категорий...");
        // ... здесь был бы парсинг JSON
        return List.of();
    }

    @Override
    protected List<Operation> parseOperations(String content) {
        System.out.println("Парсинг JSON данных операций...");
        // ... здесь был бы парсинг JSON
        return List.of();
    }

    @Override
    protected void saveAccounts(List<BankAccount> accounts) {
        System.out.println("Сохранение счетов из JSON...");
        super.saveAccounts(accounts);
    }

    @Override
    protected void saveCategories(List<Category> categories) {
        System.out.println("Сохранение категорий из JSON...");
        super.saveCategories(categories);
    }

    @Override
    protected void saveOperations(List<Operation> operations) {
        System.out.println("Сохранение операций из JSON...");
        super.saveOperations(operations);
    }
}