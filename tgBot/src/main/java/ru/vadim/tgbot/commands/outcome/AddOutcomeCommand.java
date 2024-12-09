package ru.vadim.tgbot.commands.outcome;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.ADD_OUTCOME_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.ADD_OUTCOME_COMMAND_DESCRIPTION;

@Component
public class AddOutcomeCommand implements Command {
    @Override
    public String command() {
        return ADD_OUTCOME_COMMAND;
    }

    @Override
    public String description() {
        return ADD_OUTCOME_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.ADD_OUTCOME;
    }
}
