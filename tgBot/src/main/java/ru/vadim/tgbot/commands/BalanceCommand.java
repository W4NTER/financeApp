package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

@Component
public class BalanceCommand implements Command {
    @Override
    public String command() {
        return "Баланс";
    }

    @Override
    public String description() {
        return "Проверка баланса";
    }

    @Override
    public StateType state() {
        return StateType.BALANCE;
    }
}
