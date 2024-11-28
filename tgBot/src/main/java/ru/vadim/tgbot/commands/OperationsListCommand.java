package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;

@Component
public class OperationsListCommand implements Command {

    @Override
    public String command() {
        return "/operationsList";
    }

    @Override
    public String description() {
        return "Список операций";
    }
}
