package ru.hse.bank.domain;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Operation {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    
    private final Long id;
    private final OperationType type;
    private final Long bankAccountId;
    private final double amount;
    private final LocalDateTime date;
    private String description;
    private final Long categoryId;
    
    public Operation(OperationType type, Long bankAccountId, double amount, 
                    String description, Long categoryId) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.description = description;
        this.categoryId = categoryId;
    }
    
    // Геттеры
    public Long getId() { return id; }
    public OperationType getType() { return type; }
    public Long getBankAccountId() { return bankAccountId; }
    public double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getCategoryId() { return categoryId; }
}