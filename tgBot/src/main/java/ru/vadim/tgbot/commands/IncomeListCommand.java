package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;

@Component
public class IncomeListCommand implements Command {

    @Override
    public String command() {
        return "/incomeList";
    }

    @Override
    public String description() {
        return "Список доходов";
    }
}
