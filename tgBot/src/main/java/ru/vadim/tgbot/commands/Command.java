package ru.vadim.tgbot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

import java.util.Arrays;

import static ru.vadim.tgbot.Constants.*;

@Component
public interface Command {
    String command();

    String description();

    StateType state();

    default ReplyKeyboardMarkup menu() {
        LOGGER.info("set keyboard menu");
        LOGGER.info(Arrays.toString(state().getSubMenuItems()));
        return new ReplyKeyboardMarkup(state().getSubMenuItems(), MAIN_MENU_ARR)
                .resizeKeyboard(RESIZE_KEYBOARD);
    }

    default String post() {
        return description();
    }

    default SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post()).replyMarkup(menu());
    }
}
