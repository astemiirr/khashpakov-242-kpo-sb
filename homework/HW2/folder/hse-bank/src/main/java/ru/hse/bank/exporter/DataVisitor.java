package ru.hse.bank.exporter;

import ru.hse.bank.domain.BankAccount;
import ru.hse.bank.domain.Category;
import ru.hse.bank.domain.Operation;

public interface DataVisitor {
    void visit(BankAccount account);
    void visit(Category category);
    void visit(Operation operation);
    String getResult();
}