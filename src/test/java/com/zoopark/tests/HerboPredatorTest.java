package com.zoopark.tests;

import com.zoopark.domain.animals.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HerboPredatorTest {

    @Test
    void testHerboContactZooEligibility() {
        // Тест с высокой добротой - может в контактный зоопарк
        Herbo highKindnessMonkey = new Monkey("Добрый", 2, 8);
        assertTrue(highKindnessMonkey.canBeInContactZoo());

        // Тест с низкой добротой - не может в контактный зоопарк
        Herbo lowKindnessRabbit = new Rabbit("Злой", 1, 3);
        assertFalse(lowKindnessRabbit.canBeInContactZoo());

        // Граничный случай - ровно 5 баллов
        Herbo borderCaseMonkey = new Monkey("Граничный", 2, 5);
        assertFalse(borderCaseMonkey.canBeInContactZoo());
    }

    @Test
    void testPredatorContactZooEligibility() {
        // Хищники никогда не могут в контактный зоопарк
        Predator tiger = new Tiger("Тигр", 10);
        assertFalse(tiger.canBeInContactZoo());

        Predator wolf = new Wolf("Волк", 8);
        assertFalse(wolf.canBeInContactZoo());
    }

    @Test
    void testKindnessLevelAccessors() {
        Monkey monkey = new Monkey("Тест", 2, 7);
        assertEquals(7, monkey.getKindnessLevel());

        Rabbit rabbit = new Rabbit("Тест", 1, 9);
        assertEquals(9, rabbit.getKindnessLevel());
    }
}