package ru.vadim.tgbot.commands.income;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

@Component
public class IncomeCommand implements Command {
    @Override
    public String command() {
        return "Доходы";
    }

    @Override
    public String description() {
        return "Выберите действие";
    }

    @Override
    public StateType state() {
        return StateType.INCOME;
    }
}
