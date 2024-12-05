package ru.vadim.tgbot.commands.details.liabilitiesAssets;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.LiabilitiesAssetsWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.CALCULATE_LIABILITIES_ASSETS_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.CALCULATE_LIABILITIES_ASSETS_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.constants.Constants.ASSETS_LIABILITIES_MENU;
import static ru.vadim.tgbot.constants.Constants.LOGGER;

@Component
@AllArgsConstructor
public class CalculateLiabilitiesAssetsCommand implements Command {
    private final LiabilitiesAssetsWebClient liabilitiesAssetsWebClient;

    @Override
    public String command() {
        return CALCULATE_LIABILITIES_ASSETS_COMMAND;
    }

    @Override
    public String description() {
        return CALCULATE_LIABILITIES_ASSETS_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.CALCULATE_ASSETS_LIABILITIES;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(ASSETS_LIABILITIES_MENU).resizeKeyboard(true);
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            int res = Integer.parseInt(
                            liabilitiesAssetsWebClient.getResult(
                                    update.message().chat().id()));
            return new SendMessage(update.message().chat().id(), description() + res);
        } catch (Exception e) {
            LOGGER.info(String.format("ChatId = %s, %s", update.message().chat().id(), e.getMessage()));
            return new SendMessage(update.message().chat().id(), "Что-то сломалось");
        }
    }
}
