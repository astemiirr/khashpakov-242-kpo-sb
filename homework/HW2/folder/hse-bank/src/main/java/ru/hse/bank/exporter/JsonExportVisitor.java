package ru.hse.bank.exporter;

import ru.hse.bank.domain.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class JsonExportVisitor implements DataVisitor {
    private final List<String> accountsJson = new ArrayList<>();

    @Override
    public void visit(BankAccount account) {
        String json = String.format(
                "{\"id\": %d, \"name\": \"%s\", \"balance\": %.2f}",
                account.getId(), account.getName(), account.getBalance()
        );
        accountsJson.add(json);
    }

    // Убираем неиспользуемые методы
    @Override
    public void visit(ru.hse.bank.domain.Category category) {}

    @Override
    public void visit(ru.hse.bank.domain.Operation operation) {}

    @Override
    public String getResult() {
        return String.format("{\"accounts\": [%s]}", String.join(", ", accountsJson));
    }
}