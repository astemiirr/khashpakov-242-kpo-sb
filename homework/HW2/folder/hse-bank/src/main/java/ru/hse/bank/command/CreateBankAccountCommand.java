package ru.hse.bank.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.hse.bank.domain.BankAccount;
import ru.hse.bank.facade.BankAccountFacade;

public class CreateBankAccountCommand implements Command {
    private final BankAccountFacade facade;
    private final String name;
    private final double initialBalance;
    @Getter
    private BankAccount result;

    public CreateBankAccountCommand(BankAccountFacade facade, String name, double initialBalance) {
        this.facade = facade;
        this.name = name;
        this.initialBalance = initialBalance;
    }


    @Override
    public void execute() {
        result = facade.createAccount(name, initialBalance);
        System.out.println("Создан счет: " + result.getName() + " с балансом: " + result.getBalance());
    }

    @Override
    public String getName() {
        return "Создание банковского счета";
    }

}