package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

@Component
public class GoToStartCommand implements Command {
    @Override
    public String command() {
        return "go to start";
    }

    @Override
    public String description() {
        return "Начальное меню";
    }

    @Override
    public StateType state() {
        return StateType.MAIN_MENU;
    }
}
