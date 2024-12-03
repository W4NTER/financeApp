package ru.vadim.tgbot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.Constants.*;

@Component
public class StartCommand implements Command {

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Привет, я бот который поможет тебе следить за расходами и будет держать тебя в тонусе!!! "
                + "Для прсмотра команд ты можешь ввести \"/help\"";
    }

    @Override
    public StateType state() {
        return StateType.MAIN_MENU;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        LOGGER.info("set keyboard menu");
        return new ReplyKeyboardMarkup(state().getSubMenuItems(), MAIN_MENU_ARR).resizeKeyboard(RESIZE_KEYBOARD);
    }

    @Override
    public String post() {
        return description();
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post()).replyMarkup(menu());
    }
}
