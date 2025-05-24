package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.CategoryWebClient;
import ru.vadim.tgbot.dto.response.CategoryDto;
import ru.vadim.tgbot.cashService.CashCategoriesService;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.BotAnswersConstants.EVERYTHING_WRITE_BOT;
import static ru.vadim.tgbot.constants.BotAnswersConstants.SOMETHING_WRONG_ANSWER_FROM_BOT;
import static ru.vadim.tgbot.constants.Constants.FORMAT_FOR_LOGGING_CHAT_ID;
import static ru.vadim.tgbot.constants.Constants.LOGGER;

@Component
public class AddCategoryHandler implements StateHandler {
    private final CategoryWebClient categoryWebClient;
    private final ObjectMapper objectMapper;
    private final CashCategoriesService cashCategoriesService;

    public AddCategoryHandler(
            CategoryWebClient categoryWebClient,
            ObjectMapper objectMapper,
            CashCategoriesService cashCategoriesService) {
        this.categoryWebClient = categoryWebClient;
        this.objectMapper = objectMapper;
        this.cashCategoriesService = cashCategoriesService;
    }

    @Override
    public boolean supports(String state) {
        return StateType.ADD_CATEGORY.toString().equals(state);
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        try {
            CategoryDto categoryDto = objectMapper.readValue(
                    categoryWebClient.addCategory(chatId, update.message().text()),
                    CategoryDto.class);

            LOGGER.info("category id = {}", categoryDto.categoryId());

            cashCategoriesService.updateCashCategories(chatId);
            return new SendMessage(chatId, EVERYTHING_WRITE_BOT);
        } catch (JsonProcessingException e) {
            LOGGER.error(String.format(FORMAT_FOR_LOGGING_CHAT_ID, chatId, e.getMessage()));
            return new SendMessage(chatId, SOMETHING_WRONG_ANSWER_FROM_BOT);
        }
    }
}
