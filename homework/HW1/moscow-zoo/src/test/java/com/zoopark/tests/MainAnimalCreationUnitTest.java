package com.zoopark.tests;

import com.zoopark.Main;
import com.zoopark.services.ZooService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainAnimalCreationUnitTest {

    @Mock
    private ZooService zooService;

    private void invokeAddNewAnimal(Scanner scanner, ZooService zooService) throws Exception {
        Method method = Main.class.getDeclaredMethod("addNewAnimal", Scanner.class, ZooService.class);
        method.setAccessible(true);
        method.invoke(null, scanner, zooService);
    }

    @Test
    void testAddNewAnimalMonkeySuccess() throws Exception {
        String input = "1\nTestMonkey\n3\n7\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(zooService.addAnimal(any())).thenReturn(true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        try {
            invokeAddNewAnimal(new Scanner(inputStream), zooService);

            String output = outputStream.toString().toLowerCase();
            assertTrue(output.contains("добавление"));
            assertTrue(output.contains("животного"));
            assertTrue(output.contains("успешно"));
            verify(zooService).addAnimal(any());
        } finally {
            System.setOut(System.out);
            System.setIn(System.in);
        }
    }

    @Test
    void testAddNewAnimalMonkeyRejected() throws Exception {
        String input = "1\nTestMonkey\n3\n7\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(zooService.addAnimal(any())).thenReturn(false);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        try {
            invokeAddNewAnimal(new Scanner(inputStream), zooService);

            String output = outputStream.toString().toLowerCase();
            assertTrue(output.contains("здоровья"));
            assertTrue(output.contains("проверку"));
            assertTrue(output.contains("не") || output.contains("добавлено"));
            verify(zooService).addAnimal(any());
        } finally {
            System.setOut(System.out);
            System.setIn(System.in);
        }
    }

    @Test
    void testAddNewAnimalMonkeyInvalidKindnessThenValid() throws Exception {
        String input = "1\nTestMonkey\n3\n0\n8\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(zooService.addAnimal(any())).thenReturn(true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        try {
            invokeAddNewAnimal(new Scanner(inputStream), zooService);

            String output = outputStream.toString().toLowerCase();
            assertTrue(output.contains("доброты"));
            assertTrue(output.contains("некорректно"));
            assertTrue(output.contains("1-10") || output.contains("ожидалось"));
            assertTrue(output.contains("успешно"));
            verify(zooService).addAnimal(any());
        } finally {
            System.setOut(System.out);
            System.setIn(System.in);
        }
    }

    @Test
    void testAddNewAnimalTiger() throws Exception {
        String input = "3\nTestTiger\n10\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(zooService.addAnimal(any())).thenReturn(true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        try {
            invokeAddNewAnimal(new Scanner(inputStream), zooService);

            String output = outputStream.toString().toLowerCase();
            assertTrue(output.contains("успешно"));
            verify(zooService).addAnimal(any());
        } finally {
            System.setOut(System.out);
            System.setIn(System.in);
        }
    }

    @Test
    void testAddNewAnimalInvalidType() throws Exception {
        String input = "99\nTestAnimal\n5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        try {
            invokeAddNewAnimal(new Scanner(inputStream), zooService);

            String output = outputStream.toString().toLowerCase();
            assertTrue(output.contains("неверный"));
            assertTrue(output.contains("тип"));
            assertTrue(output.contains("животного"));
            verify(zooService, never()).addAnimal(any());
        } finally {
            System.setOut(System.out);
            System.setIn(System.in);
        }
    }

    @Test
    void testAddNewAnimalRabbit() throws Exception {
        String input = "2\nTestRabbit\n2\n6\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(zooService.addAnimal(any())).thenReturn(true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        try {
            invokeAddNewAnimal(new Scanner(inputStream), zooService);

            String output = outputStream.toString().toLowerCase();
            assertTrue(output.contains("успешно"));
            verify(zooService).addAnimal(any());
        } finally {
            System.setOut(System.out);
            System.setIn(System.in);
        }
    }

    @Test
    void testAddNewAnimalWolf() throws Exception {
        String input = "4\nTestWolf\n8\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(zooService.addAnimal(any())).thenReturn(true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        try {
            invokeAddNewAnimal(new Scanner(inputStream), zooService);

            String output = outputStream.toString().toLowerCase();
            assertTrue(output.contains("успешно"));
            verify(zooService).addAnimal(any());
        } finally {
            System.setOut(System.out);
            System.setIn(System.in);
        }
    }
}