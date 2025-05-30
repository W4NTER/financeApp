package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.OperationsWebClient;
import ru.vadim.tgbot.dto.request.OperationDTO;
import ru.vadim.tgbot.dto.response.CategoryDto;
import ru.vadim.tgbot.cashService.CurrCategoryCashService;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.BotAnswersConstants.EVERYTHING_WRITE_BOT;
import static ru.vadim.tgbot.utils.constants.BotAnswersConstants.WRONG_FORMAT_BOT;
import static ru.vadim.tgbot.utils.constants.Constants.*;

@Component
public class AddIncomeHandler implements StateHandler {
    private final OperationsWebClient operationsWebClient;
    private final CurrCategoryCashService currCategoryCashService;

    public AddIncomeHandler(
            OperationsWebClient operationsWebClient,
            CurrCategoryCashService currCategoryCashService
    ) {
        this.operationsWebClient = operationsWebClient;
        this.currCategoryCashService = currCategoryCashService;
    }

    @Override
    public boolean supports(String state) {
        return StateType.ADD_INCOME.toString().equals(state);
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        CategoryDto category = currCategoryCashService.getCashCategory(chatId);
        String[] message = update.message().text().split("-");
        LOGGER.info(String.format("chatId = %s, Add income message - %s", chatId, update.message().text()));
        try {
            int sum = Integer.parseInt(message[1].trim());
            operationsWebClient.addOperation(
                    new OperationDTO(INCOME_TYPE, sum, category.categoryId(), message[0]));
            return new SendMessage(chatId, EVERYTHING_WRITE_BOT);
        } catch (Exception e) {
            LOGGER.error(String.format(FORMAT_FOR_LOGGING_CHAT_ID, chatId, e.getMessage()));
            return new SendMessage(chatId, WRONG_FORMAT_BOT);
        }
    }
}
