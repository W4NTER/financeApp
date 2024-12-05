package ru.vadim.tgbot.commands.outcome;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

@Component
public class AddOutcomeCommand implements Command {
    @Override
    public String command() {
        return "Добавить трату";
    }

    @Override
    public String description() {
        return "Введите название и сумму операции в формате:\n<название>-<сумма>";
    }

    @Override
    public StateType state() {
        return StateType.ADD_OUTCOME;
    }
}
