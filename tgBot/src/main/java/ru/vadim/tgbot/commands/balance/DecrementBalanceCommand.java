package ru.vadim.tgbot.commands.balance;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.DECREMENT_BALANCE_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.DECREMENT_BALANCE_COMMAND_DESCRIPTION;

@Component
public class DecrementBalanceCommand implements Command {
    @Override
    public String command() {
        return DECREMENT_BALANCE_COMMAND;
    }

    @Override
    public String description() {
        return DECREMENT_BALANCE_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.DECREMENT_BALANCE;
    }
}
