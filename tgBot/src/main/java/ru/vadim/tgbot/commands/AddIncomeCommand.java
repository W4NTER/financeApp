package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

@Component
public class AddIncomeCommand implements Command {
    @Override
    public String command() {
        return "Добавить доход";
    }

    @Override
    public String description() {
        return "Введите название и сумму операции в формате:\n<название>-<сумма>";
    }

    @Override
    public StateType state() {
        return StateType.ADD_INCOME;
    }
}
