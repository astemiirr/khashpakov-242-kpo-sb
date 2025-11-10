package ru.hse.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hse.bank.command.Command;
import ru.hse.bank.command.CreateBankAccountCommand;
import ru.hse.bank.command.TimingDecorator;
import ru.hse.bank.domain.ObservableBankAccount;
import ru.hse.bank.exporter.DataVisitor;
import ru.hse.bank.exporter.JsonExportVisitor;
import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.observer.LowBalanceObserver;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class BankConsole {
    private final BankAccountFacade accountFacade;
    private final DomainFactory factory;
    private final Scanner scanner = new Scanner(System.in);

    private static final String MAIN_MENU =
            "=== HSE Bank ===\n" +
            "1. Демонстрация паттернов\n" +
            "2. Создать счет\n" +
            "3. Пополнить счет\n" +
            "4. Снять деньги\n" +
            "5. Показать счета\n" +
            "6. Экспорт данных\n" +
            "7. Выйти\n" +
            "Выберите: ";

    public void start() {
        boolean running = true;

        while (running) {
            System.out.print(MAIN_MENU);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    demonstratePatterns();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    withdraw();
                    break;
                case 5:
                    showAccounts();
                    break;
                case 6:
                    exportData();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
        System.out.println("До свидания!");
    }

    private void demonstratePatterns() {
        System.out.println("\n--- Демонстрация паттернов ---");

        Command createCommand = new CreateBankAccountCommand(accountFacade, "Демо-счет", 500.0);
        Command timedCommand = new TimingDecorator(createCommand);
        timedCommand.execute();

        ObservableBankAccount obsAccount = (ObservableBankAccount) factory.createObservableBankAccount("Наблюдаемый", 300.0);
        obsAccount.addObserver(new LowBalanceObserver(100.0));
        obsAccount.withdraw(250.0);

        System.out.println("--- Демонстрация завершена ---\n");
    }

    private void createAccount() {
        System.out.print("Введите название счета: ");
        String name = scanner.nextLine();
        System.out.print("Введите начальный баланс: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // очистка буфера

        Command command = new CreateBankAccountCommand(accountFacade, name, balance);
        command.execute();
    }

    private void deposit() {
        System.out.print("Введите ID счета: ");
        Long id = scanner.nextLong();
        System.out.print("Введите сумму: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // очистка буфера

        accountFacade.deposit(id, amount);
        System.out.println("Пополнение выполнено");
    }

    private void withdraw() {
        System.out.print("Введите ID счета: ");
        Long id = scanner.nextLong();
        System.out.print("Введите сумму: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // очистка буфера

        accountFacade.withdraw(id, amount);
        System.out.println("Снятие выполнено");
    }

    private void showAccounts() {
        accountFacade.getAllAccounts().forEach(acc ->
                System.out.printf("ID: %d, Имя: %s, Баланс: %.2f%n",
                        acc.getId(), acc.getName(), acc.getBalance())
        );
    }

    private void exportData() {
        DataVisitor exporter = new JsonExportVisitor();
        accountFacade.getAllAccounts().forEach(exporter::visit);
        System.out.println("Экспорт данных:");
        System.out.println(exporter.getResult());
    }
}