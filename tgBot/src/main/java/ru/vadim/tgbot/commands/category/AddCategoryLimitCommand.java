package ru.vadim.tgbot.commands.category;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.ADD_CATEGORY_LIMIT_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.ADD_CATEGORY_LIMIT_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.constants.Constants.*;

@Component
public class AddCategoryLimitCommand implements Command {
    @Override
    public String command() {
        return ADD_CATEGORY_LIMIT_COMMAND;
    }

    @Override
    public String description() {
        return ADD_CATEGORY_LIMIT_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.ADD_CATEGORY_LIMIT;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        LOGGER.info("set keyboard menu");
        return new ReplyKeyboardMarkup(CATEGORY_MENU)
                .resizeKeyboard(RESIZE_KEYBOARD);
    }
}
