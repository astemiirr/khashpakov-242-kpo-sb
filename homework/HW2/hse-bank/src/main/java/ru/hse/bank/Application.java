package ru.hse.bank;

import ru.hse.bank.command.Command;
import ru.hse.bank.command.CreateBankAccountCommand;
import ru.hse.bank.command.TimingDecorator;
import ru.hse.bank.di.DIContainer;
import ru.hse.bank.domain.BankAccount;
import ru.hse.bank.domain.ObservableBankAccount;
import ru.hse.bank.exporter.DataVisitor;
import ru.hse.bank.exporter.JsonExportVisitor;
import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.importer.DataImporter;
import ru.hse.bank.importer.JsonDataImporter;
import ru.hse.bank.importer.XmlDataImporter;
import ru.hse.bank.observer.LowBalanceObserver;

import java.util.Optional;
import java.util.Scanner;

public class Application {
    private static BankAccountFacade accountFacade;
    private static DomainFactory factory;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Инициализация DI контейнера
        DIContainer container = new DIContainer();
        accountFacade = container.getBean(BankAccountFacade.class);
        factory = container.getBean(DomainFactory.class);

        System.out.println("=== HSE Bank Finance Tracker ===\n");

        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    demonstratePatterns();
                    break;
                case 2:
                    createAccountInteractive();
                    break;
                case 3:
                    depositInteractive();
                    break;
                case 4:
                    withdrawInteractive();
                    break;
                case 5:
                    showAllAccounts();
                    break;
                case 6:
                    exportDataInteractive();
                    break;
                case 7:
                    showAccountDetails();
                    break;
                case 8:
                    deleteAccountInteractive();
                    break;
                case 9:
                    demonstrateObserver();
                    break;
                case 10:
                    importFromJson();
                    break;
                case 11:
                    importFromXml();
                    break;
                case 0:
                    running = false;
                    System.out.println("До свидания!");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void showMainMenu() {
        System.out.println("=== Главное меню ===");
        System.out.println("1. Демонстрация всех паттернов");
        System.out.println("2. Создать новый счет");
        System.out.println("3. Пополнить счет");
        System.out.println("4. Снять со счета");
        System.out.println("5. Показать все счета");
        System.out.println("6. Экспорт данных");
        System.out.println("7. Показать детали счета");
        System.out.println("8. Удалить счет");
        System.out.println("9. Демонстрация Наблюдателя");
        System.out.println("10. Импорт из JSON");
        System.out.println("11. Импорт из XML");
        System.out.println("0. Выйти");
    }

    private static void demonstratePatterns() {
        System.out.println("\n--- Демонстрация всех паттернов ---");

        // 1. Фасад + Команда + Декоратор
        System.out.println("1. Фасад + Команда + Декоратор:");
        Command createAccountCommand = new CreateBankAccountCommand(
                accountFacade, "Демо-счет", 1000.0
        );
        Command timedCommand = new TimingDecorator(createAccountCommand);
        timedCommand.execute();

        // 2. Наблюдатель
        System.out.println("\n2. Наблюдатель:");
        demonstrateObserver();

        // 3. Посетитель для экспорта
        System.out.println("\n3. Посетитель для экспорта:");
        exportDataInteractive();

        System.out.println("--- Демонстрация завершена ---");
    }

    private static void createAccountInteractive() {
        System.out.println("\n--- Создание нового счета ---");
        System.out.print("Введите название счета: ");
        String name = scanner.nextLine();

        double balance = getDoubleInput("Введите начальный баланс: ");

        try {
            Command command = new CreateBankAccountCommand(accountFacade, name, balance);
            Command timedCommand = new TimingDecorator(command);
            timedCommand.execute();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void depositInteractive() {
        System.out.println("\n--- Пополнение счета ---");
        Long accountId = getLongInput("Введите ID счета: ");
        double amount = getDoubleInput("Введите сумму для пополнения: ");

        try {
            accountFacade.deposit(accountId, amount);
            System.out.println("Счет успешно пополнен!");
            showAccountBalance(accountId);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void withdrawInteractive() {
        System.out.println("\n--- Снятие со счета ---");
        Long accountId = getLongInput("Введите ID счета: ");
        double amount = getDoubleInput("Введите сумму для снятия: ");

        try {
            accountFacade.withdraw(accountId, amount);
            System.out.println("Снятие выполнено успешно!");
            showAccountBalance(accountId);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void showAllAccounts() {
        System.out.println("\n--- Все счета ---");
        var accounts = accountFacade.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Счетов нет");
        } else {
            accounts.forEach(account ->
                    System.out.printf("ID: %d | Имя: %-15s | Баланс: %10.2f%n",
                            account.getId(), account.getName(), account.getBalance())
            );
        }
    }

    private static void exportDataInteractive() {
        System.out.println("\n--- Экспорт данных ---");
        DataVisitor exporter = new JsonExportVisitor();
        accountFacade.getAllAccounts().forEach(exporter::visit);
        System.out.println("Экспортированные данные в JSON:");
        System.out.println(exporter.getResult());
    }

    private static void showAccountDetails() {
        System.out.println("\n--- Детали счета ---");
        Long accountId = getLongInput("Введите ID счета: ");

        Optional<BankAccount> account = accountFacade.getAccount(accountId);
        if (account.isPresent()) {
            BankAccount acc = account.get();
            System.out.printf("Детали счета:%n");
            System.out.printf("ID: %d%n", acc.getId());
            System.out.printf("Название: %s%n", acc.getName());
            System.out.printf("Баланс: %.2f%n", acc.getBalance());
        } else {
            System.out.println("Счет с ID " + accountId + " не найден");
        }
    }

    private static void deleteAccountInteractive() {
        System.out.println("\n--- Удаление счета ---");
        Long accountId = getLongInput("Введите ID счета для удаления: ");

        // Сначала проверим существование счета
        Optional<BankAccount> account = accountFacade.getAccount(accountId);
        if (account.isPresent()) {
            BankAccount acc = account.get();
            System.out.printf("Вы собираетесь удалить счет:%n");
            System.out.printf("ID: %d, Название: %s, Баланс: %.2f%n",
                    acc.getId(), acc.getName(), acc.getBalance());

            System.out.print("Вы уверены? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("y") || confirmation.equals("yes") || confirmation.equals("да")) {
                try {
                    accountFacade.deleteAccount(accountId);
                    System.out.println("Счет успешно удален!");
                } catch (Exception e) {
                    System.out.println("Ошибка при удалении счета: " + e.getMessage());
                }
            } else {
                System.out.println("Удаление отменено.");
            }
        } else {
            System.out.println("Счет с ID " + accountId + " не найден");
        }
    }

    private static void demonstrateObserver() {
        System.out.println("\n--- Демонстрация Наблюдателя ---");

        System.out.print("Введите название наблюдаемого счета: ");
        String name = scanner.nextLine();
        double initialBalance = getDoubleInput("Введите начальный баланс: ");
        double threshold = getDoubleInput("Введите порог для уведомления: ");

        try {
            ObservableBankAccount observableAccount =
                    (ObservableBankAccount) factory.createObservableBankAccount(name, initialBalance);
            observableAccount.addObserver(new LowBalanceObserver(threshold));

            System.out.println("\nСимуляция операций:");

            double depositAmount = getDoubleInput("Введите сумму для пополнения: ");
            System.out.println("Пополнение счета на " + depositAmount + "...");
            observableAccount.deposit(depositAmount);

            double withdrawAmount = getDoubleInput("Введите сумму для снятия: ");
            System.out.println("Списание " + withdrawAmount + "...");
            observableAccount.withdraw(withdrawAmount);

            System.out.printf("Итоговый баланс: %.2f%n", observableAccount.getBalance());

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void showAccountBalance(Long accountId) {
        accountFacade.getAccount(accountId).ifPresent(account ->
                System.out.printf("Текущий баланс счета '%s': %.2f%n",
                        account.getName(), account.getBalance())
        );
    }

    // Вспомогательные методы для ввода
    private static int getIntInput() {
        System.out.print("Выберите опцию: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Пожалуйста, введите целое число.");
            System.out.print("Выберите опцию: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static long getLongInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            System.out.println("Пожалуйста, введите целое число.");
            System.out.print(prompt);
            scanner.next();
        }
        long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Пожалуйста, введите число.");
            System.out.print(prompt);
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    private static void importFromJson() {
        System.out.println("\n--- Импорт из JSON ---");
        System.out.print("Введите путь к JSON файлу: ");
        String filePath = scanner.nextLine();

        DataImporter importer = new JsonDataImporter();
        importer.importData(filePath);
        System.out.println("Импорт из JSON завершен!");
    }

    private static void importFromXml() {
        System.out.println("\n--- Импорт из XML ---");
        System.out.print("Введите путь к XML файлу: ");
        String filePath = scanner.nextLine();

        DataImporter importer = new XmlDataImporter();
        importer.importData(filePath);
        System.out.println("Импорт из XML завершен!");
    }
}