package com.zoopark.domain.animals;

public class Tiger extends Predator {
    public Tiger(String name, int food) {
        super(name, food);
    }

    @Override
    public String getType() {
        return "Тигр";
    }
}