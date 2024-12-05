package ru.vadim.tgbot.commands.details.liabilitiesAssets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.LiabilitiesAssetsWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.dto.request.LiabilitiesAssetsRequest;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.Constants.ASSETS_LIABILITIES_MENU;
import static ru.vadim.tgbot.Constants.LOGGER;

@Component
@AllArgsConstructor
public class CalculateLiabilitiesAssetsCommand implements Command {
    private final LiabilitiesAssetsWebClient liabilitiesAssetsWebClient;
    private final ObjectMapper objectMapper;

    @Override
    public String command() {
        return "Рассчитать";
    }

    @Override
    public String description() {
        return "Итого: ";
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
