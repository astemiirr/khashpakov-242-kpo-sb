package com.zoopark.tests;

import com.zoopark.Main;
import com.zoopark.domain.animals.Monkey;
import com.zoopark.domain.animals.Rabbit;
import com.zoopark.services.ZooService;
import com.zoopark.services.ContactZooService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainUnitTest {

    @Mock
    private ZooService zooService;

    @Mock
    private ContactZooService contactZooService;

    // Вспомогательные методы для вызова private методов
    private void invokePrintMenu() throws Exception {
        Method method = Main.class.getDeclaredMethod("printMenu");
        method.setAccessible(true);
        method.invoke(null);
    }

    private void invokeShowAnimalCount(ZooService zooService) throws Exception {
        Method method = Main.class.getDeclaredMethod("showAnimalCount", ZooService.class);
        method.setAccessible(true);
        method.invoke(null, zooService);
    }

    private void invokeShowFoodConsumption(ZooService zooService) throws Exception {
        Method method = Main.class.getDeclaredMethod("showFoodConsumption", ZooService.class);
        method.setAccessible(true);
        method.invoke(null, zooService);
    }

    private void invokeShowContactZooAnimals(ContactZooService contactZooService) throws Exception {
        Method method = Main.class.getDeclaredMethod("showContactZooAnimals", ContactZooService.class);
        method.setAccessible(true);
        method.invoke(null, contactZooService);
    }

    @Test
    void testPrintMenu() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            invokePrintMenu();

            String output = outputStream.toString();

            assertFalse(output.isEmpty(), "Меню не должно быть пустым");
            assertTrue(output.contains("1") || output.contains("2") || output.contains("3") ||
                    output.contains("4") || output.contains("5") || output.contains("6") ||
                    output.contains("0"), "Должны быть номера опций");

            output = output.toLowerCase();

            boolean hasMenuItems = output.contains("добавить") ||
                    output.contains("животн") ||
                    output.contains("еды") ||
                    output.contains("вещ") ||
                    output.contains("выход");

            assertTrue(hasMenuItems, "Меню должно содержать основные пункты");

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testShowAnimalCount() throws Exception {
        // Arrange
        when(zooService.getAnimalCount()).thenReturn(5);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            invokeShowAnimalCount(zooService);

            // Assert - проверяем что вывод содержит число животных
            String output = outputStream.toString();
            assertFalse(output.isEmpty(), "Вывод не должен быть пустым");
            assertTrue(output.contains("5"), "Должно содержать количество животных");
            verify(zooService).getAnimalCount();
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    void testShowFoodConsumption() throws Exception {
        when(zooService.getTotalFoodConsumption()).thenReturn(25);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            invokeShowFoodConsumption(zooService);

            // Assert - проверяем что вывод содержит количество еды
            String output = outputStream.toString();
            assertFalse(output.isEmpty(), "Вывод не должен быть пустым");
            assertTrue(output.contains("25"), "Должно содержать количество еды");
            verify(zooService).getTotalFoodConsumption();
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    void testShowContactZooAnimalsWithAnimals() throws Exception {
        Monkey monkey = new Monkey("Чарли", 3, 7);
        Rabbit rabbit = new Rabbit("Пушистик", 1, 8);
        when(contactZooService.getAnimalsForContactZoo()).thenReturn(List.of(monkey, rabbit));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            invokeShowContactZooAnimals(contactZooService);

            // Assert - проверяем что вывод содержит имена животных
            String output = outputStream.toString();
            assertFalse(output.isEmpty(), "Вывод не должен быть пустым");
            assertTrue(output.contains("Чарли"), "Должно содержать имя животного");
            assertTrue(output.contains("Пушистик"), "Должно содержать имя животного");
            verify(contactZooService).getAnimalsForContactZoo();
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    void testShowContactZooAnimalsEmpty() throws Exception {
        // Arrange
        when(contactZooService.getAnimalsForContactZoo()).thenReturn(List.of());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            invokeShowContactZooAnimals(contactZooService);

            // Assert - проверяем что вывод сообщает об отсутствии животных
            String output = outputStream.toString();
            assertFalse(output.isEmpty(), "Вывод не должен быть пустым");

            // Проверяем что есть сообщение об отсутствии животных
            boolean hasNoAnimalsMessage = output.contains("Нет") || output.contains("нет") ||
                    output.contains("Отсутствуют") || output.contains("отсутствуют");
            assertTrue(hasNoAnimalsMessage, "Должно сообщать об отсутствии животных");

            verify(contactZooService).getAnimalsForContactZoo();
        } finally {
            System.setOut(System.out);
        }
    }
}