package ru.hse.bank.command;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TimingDecorator implements Command {
    private final Command command;

    @Override
    public void execute() {
        long startTime = System.currentTimeMillis();
        command.execute();
        long endTime = System.currentTimeMillis();
        System.out.printf("Команда '%s' выполнена за %d мс%n",
                command.getName(), (endTime - startTime));
    }

    @Override
    public String getName() {
        return command.getName() + " (с таймингом)";
    }
}