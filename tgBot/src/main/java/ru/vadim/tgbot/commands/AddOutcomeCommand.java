package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;

@Component
public class AddOutcomeCommand implements Command {
    @Override
    public String command() {
        return "/addOutcome";
    }

    @Override
    public String description() {
        return "Добавление расхода";
    }
}
