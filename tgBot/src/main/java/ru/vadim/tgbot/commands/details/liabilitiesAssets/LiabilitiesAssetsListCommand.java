package ru.vadim.tgbot.commands.details.liabilitiesAssets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.LiabilitiesAssetsWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.dto.request.LiabilitiesAssetsRequest;
import ru.vadim.tgbot.utils.state.StateType;

import java.util.List;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.LIABILITIES_ASSETS_COMMAND_LIST_DESCRIPTION;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.LIABILITIES_ASSETS_LIST_COMMAND;
import static ru.vadim.tgbot.utils.constants.Constants.*;

@Component
@AllArgsConstructor
public class LiabilitiesAssetsListCommand implements Command<SendMessage, SendResponse> {
    private final LiabilitiesAssetsWebClient liabilitiesAssetsWebClient;
    private final ObjectMapper objectMapper;

    @Override
    public String command() {
        return LIABILITIES_ASSETS_LIST_COMMAND;
    }

    @Override
    public String description() {
        return LIABILITIES_ASSETS_COMMAND_LIST_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.ASSETS_LIABILITIES_LIST;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(ASSETS_LIABILITIES_MENU).resizeKeyboard(true);
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        try {
            List<LiabilitiesAssetsRequest> listObj =
                    objectMapper.readValue(liabilitiesAssetsWebClient.findAllByChatId(chatId),
                            new TypeReference<>() {});

            return new SendMessage(chatId, createRequest(listObj));
        } catch (JsonProcessingException e) {
            LOGGER.info(String.format("ChatId = %s, %s", chatId, e.getMessage()));
            return new SendMessage(chatId, "Что-то сломалось");
        }
    }

    private String createRequest(List<LiabilitiesAssetsRequest> list) {
        StringBuilder builder = new StringBuilder();
        long sum = 0;
        for (LiabilitiesAssetsRequest request : list) {
            if (request.type().equals(ASSET_TYPE)) {
                builder.append(String.format("%s +%sр\n", request.title(), request.sum()));
                sum += request.sum();
            } else {
                builder.append(String.format("%s -%sр\n", request.title(), request.sum()));
                sum -= request.sum();
            }
        }
        builder.append("Итог: ").append(sum);
        return builder.toString();
    }
}
