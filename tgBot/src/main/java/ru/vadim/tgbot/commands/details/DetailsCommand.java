package ru.vadim.tgbot.commands.details;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.DETAILS_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.DETAILS_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.constants.Constants.DETAILS_MENU;

@Component
public class DetailsCommand implements Command {
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
}
