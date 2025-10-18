package com.zoopark.tests;

import com.zoopark.domain.animals.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalDomainTest {

    @Test
    void testMonkeyProperties() {
        Monkey monkey = new Monkey("Чарли", 3, 7);
        assertAll(
                () -> assertEquals("Чарли", monkey.getName()),
                () -> assertEquals(3, monkey.getFood()),
                () -> assertEquals(7, monkey.getKindnessLevel()),
                () -> assertEquals("Обезьяна", monkey.getType()),
                () -> assertTrue(monkey.canBeInContactZoo())
        );
    }

    @Test
    void testRabbitProperties() {
        Rabbit rabbit = new Rabbit("Пушистик", 1, 4);
        assertAll(
                () -> assertEquals("Пушистик", rabbit.getName()),
                () -> assertEquals(1, rabbit.getFood()),
                () -> assertEquals(4, rabbit.getKindnessLevel()),
                () -> assertEquals("Кролик", rabbit.getType()),
                () -> assertFalse(rabbit.canBeInContactZoo()) // доброта < 5
        );
    }

    @Test
    void testPredatorsProperties() {
        Tiger tiger = new Tiger("Амур", 10);
        Wolf wolf = new Wolf("Серый", 8);

        assertAll(
                () -> assertEquals("Амур", tiger.getName()),
                () -> assertEquals(10, tiger.getFood()),
                () -> assertEquals("Тигр", tiger.getType()),
                () -> assertFalse(tiger.canBeInContactZoo()),

                () -> assertEquals("Серый", wolf.getName()),
                () -> assertEquals(8, wolf.getFood()),
                () -> assertEquals("Волк", wolf.getType()),
                () -> assertFalse(wolf.canBeInContactZoo())
        );
    }

    @Test
    void testNumberAssignmentAndHealthStatus() {
        Monkey monkey = new Monkey("Тест", 2, 6);
        monkey.setNumber(123);
        monkey.setHealthy(true);

        assertAll(
                () -> assertEquals(123, monkey.getNumber()),
                () -> assertTrue(monkey.isHealthy())
        );
    }
}
