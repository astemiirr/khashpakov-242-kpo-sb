package com.zoopark.tests;

import com.zoopark.config.AppConfig;
import com.zoopark.domain.animals.Herbo;
import com.zoopark.services.ContactZooService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class ContactZooServiceTest {

    @Autowired
    private ContactZooService contactZooService;

    @Test
    void testGetAnimalsForContactZoo() {
        var contactAnimals = contactZooService.getAnimalsForContactZoo();

        contactAnimals.forEach(animal -> {
            assertInstanceOf(Herbo.class, animal);
            assertTrue(((com.zoopark.domain.animals.Herbo) animal).canBeInContactZoo());
        });
    }
}