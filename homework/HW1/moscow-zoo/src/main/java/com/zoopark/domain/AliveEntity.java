package com.zoopark.domain;

import com.zoopark.interfaces.IAlive;

public abstract class AliveEntity implements IAlive {
    private int food;

    @Override
    public int getFood() {
        return food;
    }

    @Override
    public void setFood(int food) {
        this.food = food;
    }
}