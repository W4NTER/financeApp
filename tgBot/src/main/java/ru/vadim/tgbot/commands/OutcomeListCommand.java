package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;

@Component
public class OutcomeListCommand implements Command {

    @Override
    public String command() {
        return "/outcomeList";
    }

    @Override
    public String description() {
        return "Список расходов";
    }
}
