package ru.hse.bank.domain;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Operation {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private final Long id;
    private String type; // "INCOME" или "EXPENSE"
    private final Long bankAccountId;
    private final double amount;
    private final LocalDateTime date;
    private String description;

    public Operation(String type, Long bankAccountId, double amount, String description) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.description = description;
    }
}