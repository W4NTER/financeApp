package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;

@Component
public class AddIncomeCommand implements Command {
    @Override
    public String command() {
        return "/addIncome";
    }

    @Override
    public String description() {
        return "Добавление дохода";
    }
}
