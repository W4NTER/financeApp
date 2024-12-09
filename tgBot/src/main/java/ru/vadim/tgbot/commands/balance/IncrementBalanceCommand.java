package ru.vadim.tgbot.commands.balance;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

@Component
public class IncrementBalanceCommand implements Command {
    @Override
    public String command() {
        return "Увеличить баланс";
    }

    @Override
    public String description() {
        return "Введите сумму, на которую нужно уменьшить баланс";
    }

    @Override
    public StateType state() {
        return StateType.INCREMENT_BALANCE;
    }
}
