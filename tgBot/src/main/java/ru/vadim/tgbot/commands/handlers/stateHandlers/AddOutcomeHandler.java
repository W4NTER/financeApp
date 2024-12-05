package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.OperationsWebClient;
import ru.vadim.tgbot.dto.request.OperationDTO;
import ru.vadim.tgbot.service.CategoryService;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.BotAnswersConstants.EVERYTHING_WRITE_BOT;
import static ru.vadim.tgbot.constants.BotAnswersConstants.WRONG_FORMAT_BOT;
import static ru.vadim.tgbot.constants.Constants.*;

@Component
@AllArgsConstructor
public class AddOutcomeHandler implements StateHandler {
    private final OperationsWebClient operationsWebClient;
    private final CategoryService categoryService;

    @Override
    public boolean supports(String state) {
        return StateType.ADD_OUTCOME.toString().equals(state);
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        var category = categoryService.findCategoryByChatId(chatId);
        LOGGER.info(String.format("chatId = %s, Add outcome message - %s", chatId, update.message().text()));
        String[] message = update.message().text().split("-");
        try {
            int sum = Integer.parseInt(message[1].trim());
            operationsWebClient.addOperation(
                    new OperationDTO(OUTCOME_TYPE, sum, category.categoryId(), message[0]));
            return new SendMessage(chatId, EVERYTHING_WRITE_BOT);
        } catch (Exception e) {
            LOGGER.error(String.format(FORMAT_FOR_LOGGING, chatId, e.getMessage()));
            return new SendMessage(chatId, WRONG_FORMAT_BOT);
        }
    }
}
