package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

@Component
public class CategoryAddCommand implements Command {

    @Override
    public String command() {
        return "Добавить категорию";
    }

    @Override
    public String description() {
        return "Добавление категории";
    }

    @Override
    public StateType state() {
        return StateType.CATEGORY_ADD;
    }
}
