package ru.vadim.tgbot.commands.balance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.BalanceWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.dto.response.BalanceResponse;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.BALANCE_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.BALANCE_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.utils.constants.Constants.BALANCE_MENU;
import static ru.vadim.tgbot.utils.constants.Constants.LOGGER;

@Component
@AllArgsConstructor
public class BalanceCommand implements Command<SendMessage, SendResponse> {
    private final BalanceWebClient balanceWebClient;
    private final ObjectMapper objectMapper;

    @Override
    public String command() {
        return BALANCE_COMMAND;
    }

    @Override
    public String description() {
        return BALANCE_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.BALANCE;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(BALANCE_MENU).resizeKeyboard(true);
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            var balanceJson = balanceWebClient.findBalanceById(update.message().chat().id());
            var balance = objectMapper.readValue(balanceJson, BalanceResponse.class);
            return new SendMessage(update.message().chat().id(),
                    String.format("%s\nБаланс равен %s", description(), balance.sum()))
                    .replyMarkup(menu());
        } catch (JsonProcessingException e) {
            LOGGER.info(String.format("ChatId = %s, %s", update.message().chat().id(), e.getMessage()));
            return new SendMessage(update.message().chat().id(), "Что-то сломалось").replyMarkup(menu());
        }
    }
}
