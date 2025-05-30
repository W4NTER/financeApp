package ru.vadim.tgbot.commands.category;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.Constants.*;

public class CategoryCommand implements Command<SendMessage, SendResponse> {
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

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post()).replyMarkup(menu());
    }
}
