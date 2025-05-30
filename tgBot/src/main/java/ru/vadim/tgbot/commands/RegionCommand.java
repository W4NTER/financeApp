package ru.vadim.tgbot.commands;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.ChosenInlineResult;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.utils.state.StateType;

@Component
public class RegionCommand implements Command<SendMessage, SendResponse> {
    private final FinanceAppWebClient financeAppWebClient;
    private final static Logger LOGGER = LogManager.getLogger();

    public RegionCommand(FinanceAppWebClient financeAppWebClient) {
        this.financeAppWebClient = financeAppWebClient;
    }

    @Override
    public String command() {
        return "/set_region";
    }

    @Override
    public String description() {
        return "Регион успешно установлен";
    }

    @Override
    public StateType state() {
        return StateType.MAIN_MENU;
    }

    @Override
    public SendMessage handle(Update update) {
        CallbackQuery result = update.callbackQuery();
        Long chatId = result.from().id();
        financeAppWebClient.setRegion(chatId, result.data());
        LOGGER.info(result.data());
        return new SendMessage(chatId, post()).replyMarkup(menu());
    }
}
