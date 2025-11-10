package ru.hse.bank.command;

public interface Command {
    void execute();
    String getName();
}