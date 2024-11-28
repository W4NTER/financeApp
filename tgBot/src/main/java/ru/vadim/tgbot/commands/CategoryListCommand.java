package ru.vadim.tgbot.commands;

import org.springframework.stereotype.Component;

@Component
public class CategoryListCommand implements Command {

    @Override
    public String command() {
        return "/categoryList";
    }

    @Override
    public String description() {
        return "Здесь будет список категорий";
    }
}
