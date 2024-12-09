package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;

@Component
public class BalanceCommand implements Command {
    @Override
    public String command() {
        return "/balance";
    }

    @Override
    public String description() {
        return "Проверка баланса";
    }
}
