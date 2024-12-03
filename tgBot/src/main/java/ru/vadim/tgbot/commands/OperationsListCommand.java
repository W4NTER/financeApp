package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

@Component
public class OperationsListCommand implements Command {

    @Override
    public String command() {
        return "Список операций";
    }

    @Override
    public String description() {
        return "Здесь будет список операций";
    }

    @Override
    public StateType state() {
        return StateType.OPERATIONS;
    }
}
