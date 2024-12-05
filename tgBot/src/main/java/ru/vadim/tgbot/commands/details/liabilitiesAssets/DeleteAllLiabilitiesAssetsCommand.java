package ru.vadim.tgbot.commands.details.liabilitiesAssets;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.LiabilitiesAssetsWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.DELETE_ALL_LIABILITIES_ASSETS_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.DELETE_ALL_LIABILITIES_ASSETS_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.constants.Constants.ASSETS_LIABILITIES_MENU;

@Component
@AllArgsConstructor
public class DeleteAllLiabilitiesAssetsCommand implements Command {
    private final LiabilitiesAssetsWebClient liabilitiesAssetsWebClient;

    @Override
    public String command() {
        return DELETE_ALL_LIABILITIES_ASSETS_COMMAND;
    }

    @Override
    public String description() {
        return DELETE_ALL_LIABILITIES_ASSETS_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.DELETE_ASSETS_LIABILITIES;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(ASSETS_LIABILITIES_MENU).resizeKeyboard(true);
    }

    @Override
    public SendMessage handle(Update update) {
        liabilitiesAssetsWebClient.deleteAllByChatId(update.message().chat().id());
        return Command.super.handle(update);
    }
}
