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
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private VeterinaryClinic veterinaryClinic;

    @Test
    void testInventoryServiceCreation() {
        InventoryService inventoryService = new InventoryService(new ZooService(veterinaryClinic));
        assertNotNull(inventoryService);
    }

    @Test
    void testPrintInventoryReport_NoExceptions() {
        InventoryService inventoryService = new InventoryService(new ZooService(veterinaryClinic));
        assertDoesNotThrow(inventoryService::printInventoryReport);
    }

    @Test
    void testEmptyInventoryReport() {
        InventoryService inventoryService = new InventoryService(new ZooService(veterinaryClinic));
        assertDoesNotThrow(inventoryService::printInventoryReport);
    }

    @Test
    void testInventoryContainsAnimalsAndThings() {
        when(veterinaryClinic.checkHealth(any())).thenReturn(true);
        ZooService zooService = new ZooService(veterinaryClinic);
        InventoryService inventoryService = new InventoryService(zooService);

        zooService.addAnimal(new Monkey("Тест", 2, 6));
        zooService.addThing(new Table("Стол"));

        assertTrue(zooService.getInventory().size() >= 2);
    }

    @Test
    void testInventoryAfterAnimalRejection() {
        when(veterinaryClinic.checkHealth(any())).thenReturn(false);
        ZooService zooService = new ZooService(veterinaryClinic);
        InventoryService inventoryService = new InventoryService(zooService);

        int initialSize = zooService.getInventory().size();

        zooService.addAnimal(new Monkey("Больной", 2, 6));
        zooService.addThing(new Table("Стол"));

        assertEquals(initialSize + 1, zooService.getInventory().size());
    }
}
