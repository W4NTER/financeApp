package ru.vadim.tgbot.commands.details.liabilitiesAssets;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.ADD_ASSET_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.ADD_ASSET_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.constants.Constants.ASSETS_LIABILITIES_MENU;

@Component
public class AddAssetCommand implements Command {
    @Override
    public String command() {
        return ADD_ASSET_COMMAND;
    }

    @Override
    public String description() {
        return ADD_ASSET_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.ADD_ASSET;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(ASSETS_LIABILITIES_MENU).resizeKeyboard(true);
    }
}
