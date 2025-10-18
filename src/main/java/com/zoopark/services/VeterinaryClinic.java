package com.zoopark.services;

import com.zoopark.domain.animals.Animal;
import org.springframework.stereotype.Service;

@Service
public class VeterinaryClinic {

    public boolean checkHealth(Animal animal) {
        boolean isHealthy = Math.random() > 0.5;

        if (isHealthy) {
            System.out.println(animal.getName() + " прошел проверку здоровья");
        } else {
            System.out.println(animal.getName() + " не прошел проверку здоровья");
        }

        animal.setHealthy(isHealthy);
        return isHealthy;
    }
}