package com.zoopark.tests;

import com.zoopark.config.AppConfig;
import com.zoopark.domain.animals.Monkey;
import com.zoopark.domain.things.Table;
import com.zoopark.services.VeterinaryClinic;
import com.zoopark.services.ZooService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class ZooServiceTest {

    @Autowired
    private ZooService zooService;

    @Mock
    private VeterinaryClinic veterinaryClinic;

    @BeforeEach
    void setup() {
        zooService = new ZooService(veterinaryClinic);
    }

    @Test
    void testAddAnimal() {
        when(veterinaryClinic.checkHealth(any())).thenReturn(true);
        Monkey monkey = new Monkey("Тестовая обезьяна", 2, 6);
        boolean result = zooService.addAnimal(monkey);
        assertTrue(result);
        assertTrue(monkey.getNumber() > 0);
    }

    @Test
    void testAddThing() {
        int initialCount = zooService.getInventory().size();
        zooService.addThing(new Table("Тестовый стол"));

        assertEquals(initialCount + 1, zooService.getInventory().size());
    }

    @Test
    void testGetTotalFoodConsumption() {
        int consumption = zooService.getTotalFoodConsumption();
        assertTrue(consumption >= 0);
    }

    @Test
    void testGetAnimalCount() {
        int count = zooService.getAnimalCount();
        assertTrue(count >= 0);
    }
}