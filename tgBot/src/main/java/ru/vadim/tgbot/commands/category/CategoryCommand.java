package ru.vadim.tgbot.commands.category;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.Constants.*;

public class CategoryCommand implements Command {
    private final String category;

    public CategoryCommand(String category) {
        this.category = category;
    }

    @Override
    public String command() {
        return category;
    }

    @Override
    public String description() {
        return category;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        LOGGER.info("set keyboard menu");
        return new ReplyKeyboardMarkup(CATEGORY_MENU)
                .resizeKeyboard(RESIZE_KEYBOARD);
    }

    @Override
    public StateType state() {
        return StateType.CATEGORY;
    }
}
