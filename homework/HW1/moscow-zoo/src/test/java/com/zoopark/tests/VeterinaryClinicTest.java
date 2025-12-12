package com.zoopark.tests;

import com.zoopark.config.AppConfig;
import com.zoopark.domain.animals.Monkey;
import com.zoopark.domain.animals.Rabbit;
import com.zoopark.domain.animals.Tiger;
import com.zoopark.domain.animals.Wolf;
import com.zoopark.services.VeterinaryClinic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class VeterinaryClinicTest {

    @Autowired
    private VeterinaryClinic veterinaryClinic;

    @Test
    void testCheckHealth() {
        Monkey monkey = new Monkey("Тестовый", 2, 6);
        boolean result = veterinaryClinic.checkHealth(monkey);
        assertEquals(result, monkey.isHealthy());
    }

    @Test
    void testCheckHealthMultipleAnimals() {
        Rabbit rabbit = new Rabbit("Кролик", 1, 7);
        Tiger tiger = new Tiger("Тигр", 10);
        Wolf wolf = new Wolf("Волк", 8);

        boolean rabbitResult = veterinaryClinic.checkHealth(rabbit);
        boolean tigerResult = veterinaryClinic.checkHealth(tiger);
        boolean wolfResult = veterinaryClinic.checkHealth(wolf);

        assertEquals(rabbitResult, rabbit.isHealthy());
        assertEquals(tigerResult, tiger.isHealthy());
        assertEquals(wolfResult, wolf.isHealthy());
    }

    @Test
    void testHealthConsistency() {
        // Проверяем, что при повторной проверке результат может измениться
        // (так как используется random в checkHealth)
        Monkey monkey = new Monkey("Тест", 2, 6);
        veterinaryClinic.checkHealth(monkey);

        // Создаем нового для чистоты теста
        Monkey monkey2 = new Monkey("Тест2", 2, 6);
        veterinaryClinic.checkHealth(monkey2);
    }
}