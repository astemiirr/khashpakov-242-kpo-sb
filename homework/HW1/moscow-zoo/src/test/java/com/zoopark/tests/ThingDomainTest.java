package com.zoopark.tests;

import com.zoopark.domain.things.Table;
import com.zoopark.domain.things.Computer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThingDomainTest {

    @Test
    void testTableCreation() {
        Table table = new Table("Стол администратора");
        assertEquals("Стол администратора", table.getName());
        table.setNumber(1001);
        assertEquals(1001, table.getNumber());
    }

    @Test
    void testComputerCreation() {
        Computer computer = new Computer("Компьютер ветеринара");
        assertEquals("Компьютер ветеринара", computer.getName());
        computer.setNumber(1002);
        assertEquals(1002, computer.getNumber());
    }
}