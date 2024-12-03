package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

@Component
public class OutcomeListCommand implements Command {

    @Override
    public String command() {
        return "Список расходов";
    }

    @Override
    public String description() {
        return "Здесь будет список расходов";
    }

    @Override
    public StateType state() {
        return StateType.OUTCOME_LIST;
    }
}
