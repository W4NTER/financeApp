package ru.vadim.tgbot.commands.handlers;

import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.CategoryCommand;
import ru.vadim.tgbot.commands.Command;

@Component
public class CategoryCommandHandler {

    public Command createCategoryListCommand(String command) {
        return new CategoryCommand(command);
    }
}
