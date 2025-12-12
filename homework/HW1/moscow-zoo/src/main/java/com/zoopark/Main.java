package com.zoopark;

import com.zoopark.domain.animals.*;
import com.zoopark.domain.things.Computer;
import com.zoopark.domain.things.Table;
import com.zoopark.services.ContactZooService;
import com.zoopark.services.InventoryService;
import com.zoopark.services.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    public ZooService zooService;

    @Autowired
    public ContactZooService contactZooService;

    @Autowired
    public InventoryService inventoryService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addNewAnimal(scanner, zooService);
                    break;
                case "2":
                    showAnimalCount(zooService);
                    break;
                case "3":
                    showFoodConsumption(zooService);
                    break;
                case "4":
                    showContactZooAnimals(contactZooService);
                    break;
                case "5":
                    inventoryService.printInventoryReport();
                    break;
                case "6":
                    addNewThing(scanner, zooService);
                    break;
                case "0":
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== МОСКОВСКИЙ ЗООПАРК ===");
        System.out.println("1. Добавить новое животное");
        System.out.println("2. Показать количество животных");
        System.out.println("3. Показать потребление еды");
        System.out.println("4. Животные для контактного зоопарка");
        System.out.println("5. Инвентаризационный отчет");
        System.out.println("6. Добавить новую вещь");
        System.out.println("0. Выход");
        System.out.print("Выберите опцию: ");
    }

    public static void addNewAnimal(Scanner scanner, ZooService zooService) {
        System.out.println("\n--- Добавление нового животного ---");
        System.out.println("Выберите тип животного:");
        System.out.println("1. Обезьяна");
        System.out.println("2. Кролик");
        System.out.println("3. Тигр");
        System.out.println("4. Волк");
        System.out.print("Ваш выбор: ");

        String typeChoice = scanner.nextLine();
        System.out.print("Введите имя животного: ");
        String name = scanner.nextLine();
        System.out.print("Введите потребление еды (кг/день): ");
        int food = Integer.parseInt(scanner.nextLine());

        Animal animal = null;

        switch (typeChoice) {
            case "1":
                System.out.print("Введите уровень доброты (1-10): ");
                int kindness1 = Integer.parseInt(scanner.nextLine());
                while (kindness1 < 1 || kindness1 > 10) {
                    System.out.println(
                            "Значение уровня доброты некорректно: " + kindness1 + ". Ожидалось: 1-10"
                    );
                    System.out.print("Введите уровень доброты (1-10): ");
                    kindness1 = Integer.parseInt(scanner.nextLine());
                }
                animal = new Monkey(name, food, kindness1);
                break;
            case "2":
                System.out.print("Введите уровень доброты (1-10): ");
                int kindness2 = Integer.parseInt(scanner.nextLine());
                while (kindness2 < 1 || kindness2 > 10) {
                    System.out.println(
                        "Значение уровня доброты некорректно: " + kindness2 + ". Ожидалось: 1-10"
                    );
                    System.out.print("Введите уровень доброты (1-10): ");
                    kindness2 = Integer.parseInt(scanner.nextLine());
                }
                animal = new Rabbit(name, food, kindness2);
                break;
            case "3":
                animal = new Tiger(name, food);
                break;
            case "4":
                animal = new Wolf(name, food);
                break;
            default:
                System.out.println("Неверный выбор типа животного.");
                return;
        }

        if (zooService.addAnimal(animal)) {
            System.out.println("Животное успешно добавлено в зоопарк!");
        } else {
            System.out.println("Животное не прошло проверку здоровья и не может быть добавлено.");
        }
    }

    private static void addNewThing(Scanner scanner, ZooService zooService) {
        System.out.println("\n--- Добавление новой вещи ---");
        System.out.println("Выберите тип вещи:");
        System.out.println("1. Стол");
        System.out.println("2. Компьютер");
        System.out.print("Ваш выбор: ");

        String choice = scanner.nextLine();
        System.out.print("Введите название вещи: ");
        String name = scanner.nextLine();

        switch (choice) {
            case "1":
                zooService.addThing(new Table(name));
                break;
            case "2":
                zooService.addThing(new Computer(name));
                break;
            default:
                System.out.println("Неверный выбор типа вещи.");
                return;
        }

        System.out.println("Вещь успешно добавлена в инвентарь!");
    }

    private static void showAnimalCount(ZooService zooService) {
        System.out.println("\n--- Количество животных ---");
        System.out.println("Всего животных в зоопарке: " + zooService.getAnimalCount());
    }

    private static void showFoodConsumption(ZooService zooService) {
        System.out.println("\n--- Потребление еды ---");
        System.out.println("Общее потребление еды всеми животными: " +
                zooService.getTotalFoodConsumption() + " кг/день");
    }

    private static void showContactZooAnimals(ContactZooService contactZooService) {
        System.out.println("\n--- Животные для контактного зоопарка ---");
        var contactAnimals = contactZooService.getAnimalsForContactZoo();

        if (contactAnimals.isEmpty()) {
            System.out.println("Нет животных, подходящих для контактного зоопарка.");
        } else {
            contactAnimals.forEach(animal ->
                    System.out.println(animal.getName() + " (" + animal.getType() + ")"));
        }
    }
}