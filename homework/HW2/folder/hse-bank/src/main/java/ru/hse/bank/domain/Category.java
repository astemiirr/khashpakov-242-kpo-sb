package ru.hse.bank.domain;

import lombok.Data;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Category {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private final Long id;
    private String name;
    private String type; // "INCOME" или "EXPENSE"

    public Category(String name, String type) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.name = name;
        this.type = type;
    }
}