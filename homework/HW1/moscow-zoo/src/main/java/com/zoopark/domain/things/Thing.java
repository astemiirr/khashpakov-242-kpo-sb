package com.zoopark.domain.things;

import com.zoopark.interfaces.IInventory;

public abstract class Thing implements IInventory {
    private int number;
    private String name;

    public Thing(String name) {
        this.name = name;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String getName() {
        return name;
    }
}