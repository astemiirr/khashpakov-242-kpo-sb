package com.zoopark.domain.animals;

public class Wolf extends Predator {
    public Wolf(String name, int food) {
        super(name, food);
    }

    @Override
    public String getType() {
        return "Волк";
    }
}