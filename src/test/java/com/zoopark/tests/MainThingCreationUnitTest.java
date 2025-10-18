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
class MainThingCreationUnitTest {

    @Mock
    private ZooService zooService;

    private void invokeAddNewThing(Scanner scanner, ZooService zooService) throws Exception {
        Method method = Main.class.getDeclaredMethod("addNewThing", Scanner.class, ZooService.class);
        method.setAccessible(true);
        method.invoke(null, scanner, zooService);
    }

    @Test
    void testAddNewThingTable() throws Exception {
        String input = "1\nTest Table\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        try {
            invokeAddNewThing(new Scanner(inputStream), zooService);

            String output = outputStream.toString().toLowerCase();
            assertTrue(output.contains("добавление"));
            assertTrue(output.contains("вещ"));
            assertTrue(output.contains("успешно"));
            assertTrue(output.contains("инвентар"));
            verify(zooService).addThing(any());
        } finally {
            System.setOut(System.out);
            System.setIn(System.in);
        }
    }

    @Test
    void testAddNewThingComputer() throws Exception {
        String input = "2\nTest Computer\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        try {
            invokeAddNewThing(new Scanner(inputStream), zooService);

            String output = outputStream.toString().toLowerCase();
            assertTrue(output.contains("успешно"));
            assertTrue(output.contains("инвентар"));
            verify(zooService).addThing(any());
        } finally {
            System.setOut(System.out);
            System.setIn(System.in);
        }
    }

    @Test
    void testAddNewThingInvalidType() throws Exception {
        String input = "99\nTest Thing\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setIn(inputStream);

        try {
            invokeAddNewThing(new Scanner(inputStream), zooService);

            String output = outputStream.toString().toLowerCase();
            assertTrue(output.contains("неверный"));
            assertTrue(output.contains("тип"));
            assertTrue(output.contains("вещ"));
            verify(zooService, never()).addThing(any());
        } finally {
            System.setOut(System.out);
            System.setIn(System.in);
        }
    }
}