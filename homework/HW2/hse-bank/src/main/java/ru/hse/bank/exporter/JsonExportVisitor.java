package ru.hse.bank.exporter;

import ru.hse.bank.domain.BankAccount;
import ru.hse.bank.domain.Category;
import ru.hse.bank.domain.Operation;

import java.util.ArrayList;
import java.util.List;

public class JsonExportVisitor implements DataVisitor {
    private final List<String> accountsJson = new ArrayList<>();
    private final List<String> categoriesJson = new ArrayList<>();
    private final List<String> operationsJson = new ArrayList<>();
    
    @Override
    public void visit(BankAccount account) {
        String json = String.format(
            "{\"id\": %d, \"name\": \"%s\", \"balance\": %.2f}",
            account.getId(), account.getName(), account.getBalance()
        );
        accountsJson.add(json);
    }
    
    @Override
    public void visit(Category category) {
        String json = String.format(
            "{\"id\": %d, \"type\": \"%s\", \"name\": \"%s\"}",
            category.getId(), category.getType(), category.getName()
        );
        categoriesJson.add(json);
    }
    
    @Override
    public void visit(Operation operation) {
        String json = String.format(
            "{\"id\": %d, \"type\": \"%s\", \"amount\": %.2f, \"description\": \"%s\"}",
            operation.getId(), operation.getType(), operation.getAmount(), 
            operation.getDescription() != null ? operation.getDescription() : ""
        );
        operationsJson.add(json);
    }
    
    @Override
    public String getResult() {
        return String.format(
            "{\"accounts\": [%s], \"categories\": [%s], \"operations\": [%s]}",
            String.join(", ", accountsJson),
            String.join(", ", categoriesJson),
            String.join(", ", operationsJson)
        );
    }
}