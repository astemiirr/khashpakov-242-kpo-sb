package com.zoopark.services;

import com.zoopark.domain.animals.Animal;
import com.zoopark.interfaces.IInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZooService {
    private final List<Animal> animals = new ArrayList<>();
    private final List<IInventory> inventory = new ArrayList<>();
    private final VeterinaryClinic veterinaryClinic;

    private int nextAnimalNumber = 1;
    private int nextThingNumber = 1001;

    @Autowired
    public ZooService(VeterinaryClinic veterinaryClinic) {
        this.veterinaryClinic = veterinaryClinic;
    }

    public boolean addAnimal(Animal animal) {
        if (veterinaryClinic.checkHealth(animal)) {
            animal.setNumber(nextAnimalNumber++);
            animals.add(animal);
            inventory.add(animal);
            return true;
        }
        return false;
    }

    public void addThing(IInventory thing) {
        thing.setNumber(nextThingNumber++);
        inventory.add(thing);
    }

    public List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    public List<IInventory> getInventory() {
        return new ArrayList<>(inventory);
    }

    public int getTotalFoodConsumption() {
        return animals.stream().mapToInt(Animal::getFood).sum();
    }

    public int getAnimalCount() {
        return animals.size();
    }
}