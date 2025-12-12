package com.zoopark.services;

import com.zoopark.domain.animals.Animal;
import com.zoopark.domain.animals.Herbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactZooService {
    private final ZooService zooService;

    @Autowired
    public ContactZooService(ZooService zooService) {
        this.zooService = zooService;
    }

    public List<Animal> getAnimalsForContactZoo() {
        return zooService.getAnimals().stream()
                .filter(animal -> animal instanceof Herbo)
                .filter(animal -> ((Herbo) animal).canBeInContactZoo())
                .collect(Collectors.toList());
    }
}