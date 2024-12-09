package ru.vadim.tgbot.commands.outcome;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.OUTCOME_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.OUTCOME_COMMAND_DESCRIPTION;

@Component
public class OutcomeCommand implements Command {
    @Override
    public String command() {
        return OUTCOME_COMMAND;
    }

    @Override
    public String description() {
        return OUTCOME_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.OUTCOME;
    }
}
