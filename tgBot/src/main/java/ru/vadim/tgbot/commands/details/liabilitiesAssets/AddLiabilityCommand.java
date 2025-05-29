package ru.vadim.tgbot.commands.details.liabilitiesAssets;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.ADD_LIABILITY_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.ADD_LIABILITY_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.utils.constants.Constants.ASSETS_LIABILITIES_MENU;

@Component
public class AddLiabilityCommand implements Command<SendMessage, SendResponse> {
    @Override
    public String command() {
        return ADD_LIABILITY_COMMAND;
    }

    @Override
    public String description() {
        return ADD_LIABILITY_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.ADD_LIABILITY;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(ASSETS_LIABILITIES_MENU).resizeKeyboard(true);
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post());
    }
}
