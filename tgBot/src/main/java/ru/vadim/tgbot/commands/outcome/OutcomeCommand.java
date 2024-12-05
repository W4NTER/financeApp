package ru.vadim.tgbot.commands.outcome;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

@Component
public class OutcomeCommand implements Command {
    @Override
    public String command() {
        return "Расходы";
    }

    @Override
    public String description() {
        return "Выберите действие";
    }

    @Override
    public StateType state() {
        return StateType.OUTCOME;
    }
}
