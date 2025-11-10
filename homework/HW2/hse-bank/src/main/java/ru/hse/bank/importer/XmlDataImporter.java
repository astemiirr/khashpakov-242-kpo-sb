package ru.hse.bank.importer;

import ru.hse.bank.domain.BankAccount;
import ru.hse.bank.domain.Category;
import ru.hse.bank.domain.Operation;

import java.util.List;

public class XmlDataImporter extends DataImporter {

    @Override
    protected String readFile(String filePath) {
        System.out.println("Чтение XML файла: " + filePath);
        // ... здесь был бы код для чтения XML файла
        return "<?xml version=\"1.0\"?><data><accounts/><categories/><operations/></data>";
    }

    @Override
    protected List<BankAccount> parseAccounts(String content) {
        System.out.println("Парсинг XML данных счетов...");
        // ... здесь был бы парсинг XML
        return List.of();
    }

    @Override
    protected List<Category> parseCategories(String content) {
        System.out.println("Парсинг XML данных категорий...");
        // ... здесь был бы парсинг XML
        return List.of();
    }

    @Override
    protected List<Operation> parseOperations(String content) {
        System.out.println("Парсинг XML данных операций...");
        // ... здесь был бы парсинг XML
        return List.of();
    }

    @Override
    protected void saveAccounts(List<BankAccount> accounts) {
        System.out.println("Сохранение счетов из XML...");
        super.saveAccounts(accounts);
    }

    @Override
    protected void saveCategories(List<Category> categories) {
        System.out.println("Сохранение категорий из XML...");
        super.saveCategories(categories);
    }

    @Override
    protected void saveOperations(List<Operation> operations) {
        System.out.println("Сохранение операций из XML...");
        super.saveOperations(operations);
    }
}