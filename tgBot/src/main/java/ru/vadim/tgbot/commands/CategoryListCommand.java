package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

@Component
public class CategoryListCommand implements Command {

    @Override
    public String command() {
        return "Категории";
    }

    @Override
    public String description() {
        return "Здесь будет список категорий";
    }

    @Override
    public StateType state() {
        return StateType.CATEGORY;
    }
}
