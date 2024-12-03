package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

@Component
public class IncomeListCommand implements Command {

    @Override
    public String command() {
        return "Список доходов";
    }

    @Override
    public String description() {
        return "Здесь будет список доходов";
    }

    @Override
    public StateType state() {
        return StateType.INCOME_LIST;
    }
}
