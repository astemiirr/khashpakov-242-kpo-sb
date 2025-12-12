package ru.hse.bank.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Category {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    
    private final Long id;
    private final CategoryType type;
    private String name;
    
    public Category(CategoryType type, String name) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.type = type;
        this.name = name;
    }
    
    // Геттеры
    public Long getId() { return id; }
    public CategoryType getType() { return type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}