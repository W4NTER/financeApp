package ru.vadim.tgbot.commands.details.liabilitiesAssets;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.LIABILITIES_ASSETS_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.LIABILITIES_ASSETS_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.constants.Constants.ASSETS_LIABILITIES_MENU;

@Component
public class LiabilitiesAssetsCommand implements Command {
    @Override
    public String command() {
        return LIABILITIES_ASSETS_COMMAND;
    }

    @Override
    public String description() {
        return LIABILITIES_ASSETS_COMMAND_DESCRIPTION;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(ASSETS_LIABILITIES_MENU).resizeKeyboard(true);
    }

    @Override
    public StateType state() {
        return StateType.ASSETS_LIABILITIES;
    }
}
