package com.zoopark.tests;

import com.zoopark.domain.animals.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HerboPredatorTest {

    @Test
    void testHerboContactZooEligibility() {
        Herbo kindMonkey = new Monkey("Добрый", 2, 8);
        Herbo meanRabbit = new Rabbit("Злой", 1, 3);
        Herbo borderlineMonkey = new Monkey("Граничный", 2, 5);

        assertAll("Проверка допуска Herbo в контактный зоопарк",
                () -> assertTrue(kindMonkey.canBeInContactZoo()),
                () -> assertFalse(meanRabbit.canBeInContactZoo()),
                () -> assertFalse(borderlineMonkey.canBeInContactZoo())
        );
    }

    @Test
    void testPredatorContactZooEligibility() {
        Predator tiger = new Tiger("Тигр", 10);
        Predator wolf = new Wolf("Волк", 8);

        assertAll("Хищники не допускаются в контактный зоопарк",
                () -> assertFalse(tiger.canBeInContactZoo()),
                () -> assertFalse(wolf.canBeInContactZoo())
        );
    }

    @Test
    void testKindnessLevelAccessors() {
        assertAll("Проверка уровней доброты",
                () -> assertEquals(7, new Monkey("Тест", 2, 7).getKindnessLevel()),
                () -> assertEquals(9, new Rabbit("Тест", 1, 9).getKindnessLevel())
        );
    }
}
