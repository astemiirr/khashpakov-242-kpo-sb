package com.zoopark.tests;

import com.zoopark.domain.animals.Monkey;
import com.zoopark.domain.animals.Rabbit;
import com.zoopark.domain.animals.Tiger;
import com.zoopark.services.VeterinaryClinic;
import com.zoopark.services.ZooService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ZooServiceEdgeCasesTest {

    @Mock
    private VeterinaryClinic veterinaryClinic;

    private ZooService zooService;

    @BeforeEach
    void setup() {
        when(veterinaryClinic.checkHealth(any())).thenReturn(true);
        zooService = new ZooService(veterinaryClinic);
    }

    @Test
    void testMultipleAnimalAddition() {
        int initialCount = zooService.getAnimalCount();

        Monkey monkey1 = new Monkey("Обезьяна1", 2, 6);
        Monkey monkey2 = new Monkey("Обезьяна2", 3, 7);
        Rabbit rabbit = new Rabbit("Кролик", 1, 8);

        zooService.addAnimal(monkey1);
        zooService.addAnimal(monkey2);
        zooService.addAnimal(rabbit);

        assertEquals(initialCount + 3, zooService.getAnimalCount());
    }

    @Test
    void testFoodConsumptionCalculation() {
        int initialConsumption = zooService.getTotalFoodConsumption();

        Monkey monkey = new Monkey("ТестЕда", 5, 6);
        Tiger tiger = new Tiger("ТестЕда2", 15);

        if (zooService.addAnimal(monkey)) {
            initialConsumption += 5;
        }
        if (zooService.addAnimal(tiger)) {
            initialConsumption += 15;
        }

        // Потребление еды должно увеличиться на сумму добавленных животных
        assertTrue(zooService.getTotalFoodConsumption() >= initialConsumption);
    }

    @Test
    void testAnimalNumberIncrement() {
        Monkey monkey1 = new Monkey("ТестНомер1", 2, 6);
        Monkey monkey2 = new Monkey("ТестНомер2", 3, 7);

        zooService.addAnimal(monkey1);
        zooService.addAnimal(monkey2);

        // Номера должны быть разными и увеличиваться
        assertNotEquals(monkey1.getNumber(), monkey2.getNumber());
        assertTrue(monkey2.getNumber() > monkey1.getNumber());
    }
}