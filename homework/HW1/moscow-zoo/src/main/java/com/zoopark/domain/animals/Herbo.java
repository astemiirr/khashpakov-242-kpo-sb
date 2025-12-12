package com.zoopark.domain.animals;

public abstract class Herbo extends Animal {
    private final int kindnessLevel;

    public Herbo(String name, int food, int kindnessLevel) {
        super(name, food);
        this.kindnessLevel = kindnessLevel;
    }

    public int getKindnessLevel() {
        return kindnessLevel;
    }

    @Override
    public boolean canBeInContactZoo() {
        return getKindnessLevel() > 5;
    }
}