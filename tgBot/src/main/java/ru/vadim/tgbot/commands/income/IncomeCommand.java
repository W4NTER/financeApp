package ru.vadim.tgbot.commands.income;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.INCOME_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.INCOME_COMMAND_DESCRIPTION;

@Component
public class IncomeCommand implements Command {
    @Override
    public String command() {
        return INCOME_COMMAND;
    }

    @Override
    public String description() {
        return INCOME_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.INCOME;
    }
}
