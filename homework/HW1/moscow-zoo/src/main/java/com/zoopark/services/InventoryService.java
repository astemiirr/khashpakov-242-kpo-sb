package com.zoopark.services;

import com.zoopark.interfaces.IInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final ZooService zooService;

    @Autowired
    public InventoryService(ZooService zooService) {
        this.zooService = zooService;
    }

    public void printInventoryReport() {
        List<IInventory> inventory = zooService.getInventory();

        System.out.println("\n=== ИНВЕНТАРИЗАЦИОННЫЙ ОТЧЕТ ===");
        for (IInventory item : inventory) {
            System.out.printf("№%d: %s%n", item.getNumber(), item.getName());
        }
        System.out.println("================================");
    }
}