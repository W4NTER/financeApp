package ru.vadim.tgbot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import ru.vadim.tgbot.utils.state.StateType;

import java.util.Arrays;

import static ru.vadim.tgbot.utils.constants.Constants.*;

public interface Command <T extends BaseRequest<T, R>, R extends BaseResponse> {
    String command();

    String description();

    StateType state();

    default ReplyKeyboardMarkup menu() {
        LOGGER.info("set keyboard menu");
        LOGGER.info(Arrays.toString(state().getSubMenuItems()));
        return new ReplyKeyboardMarkup(state().getSubMenuItems(), MAIN_MENU_ARR)
                .resizeKeyboard(RESIZE_KEYBOARD);
    }

    T handle(Update update);

    default String post() {
        return description();
    }
}
