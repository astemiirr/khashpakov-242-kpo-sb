package com.zoopark.tests;

import com.zoopark.domain.animals.Monkey;
import com.zoopark.domain.things.Table;
import com.zoopark.services.InventoryService;
import com.zoopark.services.VeterinaryClinic;
import com.zoopark.services.ZooService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private VeterinaryClinic veterinaryClinic;

    @Test
    void testPrintInventoryReport() {
        ZooService zooService = new ZooService(veterinaryClinic);
        InventoryService inventoryService = new InventoryService(zooService);

        // Act & Assert - проверяем что метод не выбрасывает исключений
        assertDoesNotThrow(() -> inventoryService.printInventoryReport());
    }

    @Test
    void testInventoryContainsAnimalsAndThings() {
        when(veterinaryClinic.checkHealth(any())).thenReturn(true);
        ZooService zooService = new ZooService(veterinaryClinic);
        InventoryService inventoryService = new InventoryService(zooService);

        Monkey monkey = new Monkey("Тестовый", 2, 6);
        zooService.addAnimal(monkey);
        zooService.addThing(new Table("Тестовый стол"));

        int inventorySize = zooService.getInventory().size();
        assertTrue(inventorySize >= 2, "Инвентарь должен содержать как минимум животное и вещь. Текущий размер: " + inventorySize);
    }

    @Test
    void testInventoryAfterAnimalRejection() {
        // Arrange - настраиваем мок чтобы животное НЕ проходило проверку здоровья
        when(veterinaryClinic.checkHealth(any())).thenReturn(false);
        ZooService zooService = new ZooService(veterinaryClinic);
        InventoryService inventoryService = new InventoryService(zooService);

        // Act - пытаемся добавить животное (оно должно быть отклонено)
        int initialSize = zooService.getInventory().size();
        Monkey monkey = new Monkey("Больной", 2, 6);
        zooService.addAnimal(monkey);
        zooService.addThing(new Table("Тестовый стол"));

        // Assert - животное не добавилось, но вещь добавилась
        int newSize = zooService.getInventory().size();
        assertEquals(initialSize + 1, newSize, "Должна добавиться только вещь, но не животное");
    }

    @Test
    void testInventoryServiceCreation() {
        ZooService zooService = new ZooService(veterinaryClinic);

        InventoryService inventoryService = new InventoryService(zooService);

        assertNotNull(inventoryService, "InventoryService должен создаваться успешно");
    }

    @Test
    void testEmptyInventoryReport() {
        ZooService zooService = new ZooService(veterinaryClinic);
        InventoryService inventoryService = new InventoryService(zooService);

        // Act & Assert - проверяем что метод работает даже с пустым инвентарем
        assertDoesNotThrow(() -> inventoryService.printInventoryReport(),
                "Метод printInventoryReport должен работать с пустым инвентарем");
    }
}