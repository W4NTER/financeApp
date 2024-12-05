package ru.vadim.tgbot.commands.balance;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

@Component
public class DecrementBalanceCommand implements Command {
    @Override
    public String command() {
        return "Уменьшить баланс";
    }

    @Override
    public String description() {
        return "Введите сумму, на которую хотите уменьшить баланс";
    }

    @Override
    public StateType state() {
        return StateType.DECREMENT_BALANCE;
    }
}
