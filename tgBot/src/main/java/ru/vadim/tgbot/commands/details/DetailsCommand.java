package ru.vadim.tgbot.commands.details;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.DETAILS_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.DETAILS_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.utils.constants.Constants.DETAILS_MENU;

@Component
public class DetailsCommand implements Command<SendMessage, SendResponse> {
    @Override
    public String command() {
        return DETAILS_COMMAND;
    }

    @Override
    public String description() {
        return DETAILS_COMMAND_DESCRIPTION;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(DETAILS_MENU).resizeKeyboard(true);
    }

    @Override
    public StateType state() {
        return StateType.DETAILS;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post()).replyMarkup(menu());
    }
}
