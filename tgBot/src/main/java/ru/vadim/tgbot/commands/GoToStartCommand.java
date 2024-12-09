package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.GO_TO_START_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.GO_TO_START_COMMAND_DESCRIPTION;

@Component
public class GoToStartCommand implements Command {
    @Override
    public String command() {
        return GO_TO_START_COMMAND;
    }

    @Override
    public String description() {
        return GO_TO_START_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.MAIN_MENU;
    }
}
