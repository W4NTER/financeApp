package ru.vadim.tgbot.commands.category;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.ADD_CATEGORY_LIMIT_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.ADD_CATEGORY_LIMIT_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.utils.constants.Constants.*;

@Component
public class AddCategoryLimitCommand implements Command<SendMessage, SendResponse> {
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

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post()).replyMarkup(menu());
    }
}
