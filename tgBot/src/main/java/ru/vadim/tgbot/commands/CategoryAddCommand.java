package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;

@Component
public class CategoryAddCommand implements Command {

    @Override
    public String command() {
        return "/categoryAdd";
    }

    @Override
    public String description() {
        return "Добавление категории";
    }
}
