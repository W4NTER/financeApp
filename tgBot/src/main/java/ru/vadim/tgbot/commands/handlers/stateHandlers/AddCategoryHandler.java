package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.CategoryWebClient;
import ru.vadim.tgbot.dto.request.CategoryRequest;
import ru.vadim.tgbot.service.CategoryService;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.BotAnswersConstants.EVERYTHING_WRITE_BOT;
import static ru.vadim.tgbot.constants.BotAnswersConstants.SOMETHING_WRONG_ANSWER_FROM_BOT;
import static ru.vadim.tgbot.constants.Constants.FORMAT_FOR_LOGGING;
import static ru.vadim.tgbot.constants.Constants.LOGGER;

@Component
@AllArgsConstructor
public class AddCategoryHandler implements StateHandler {
    private final CategoryService categoryService;
    private final CategoryWebClient categoryWebClient;
    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(String state) {
        return StateType.ADD_CATEGORY.toString().equals(state);
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        try {
            var cat = categoryService.addOrEditCategory(objectMapper
                    .readValue(categoryWebClient
                            .addCategory(chatId, update.message().text()), CategoryRequest.class), chatId);
            LOGGER.info(cat.categoryId());
            return new SendMessage(chatId, EVERYTHING_WRITE_BOT);
        } catch (JsonProcessingException e) {
            LOGGER.error(String.format(FORMAT_FOR_LOGGING, chatId, e.getMessage()));
            return new SendMessage(chatId, SOMETHING_WRONG_ANSWER_FROM_BOT);
        }
    }
}
