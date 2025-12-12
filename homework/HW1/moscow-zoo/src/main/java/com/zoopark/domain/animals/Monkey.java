package com.zoopark.domain.animals;

public class Monkey extends Herbo {
    public Monkey(String name, int food, int kindnessLevel) {
        super(name, food, kindnessLevel);
    }

    @Override
    public String getType() {
        return "Обезьяна";
    }
}