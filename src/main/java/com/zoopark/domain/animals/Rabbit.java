package com.zoopark.domain.animals;

public class Rabbit extends Herbo {
    public Rabbit(String name, int food, int kindnessLevel) {
        super(name, food, kindnessLevel);
    }

    @Override
    public String getType() {
        return "Кролик";
    }
}