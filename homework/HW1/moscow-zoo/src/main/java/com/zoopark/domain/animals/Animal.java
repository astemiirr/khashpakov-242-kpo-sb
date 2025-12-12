package com.zoopark.domain.animals;

import com.zoopark.domain.AliveEntity;
import com.zoopark.interfaces.IInventory;

public abstract class Animal extends AliveEntity implements IInventory {
    private int number;
    private final String name;
    private boolean isHealthy;

    public Animal(String name, int food) {
        this.name = name;
        setFood(food);
        this.isHealthy = false;
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

    public boolean isHealthy() {
        return isHealthy;
    }

    public void setHealthy(boolean healthy) {
        isHealthy = healthy;
    }

    public abstract String getType();

    public boolean canBeInContactZoo() {
        return false;
    }
}