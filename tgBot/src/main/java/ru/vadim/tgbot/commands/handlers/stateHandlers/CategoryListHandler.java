package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.CategoryWebClient;
import ru.vadim.tgbot.commands.handlers.CategoryCommandHandler;
import ru.vadim.tgbot.dto.request.CategoryRequest;
import ru.vadim.tgbot.service.CategoryService;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.Constants.LOGGER;

@Component
@AllArgsConstructor
public class CategoryListHandler implements StateHandler {
    private final CategoryCommandHandler categoryCommandHandler;
    private final CategoryService categoryService;
    private final CategoryWebClient categoryWebClient;
    private final ObjectMapper objectMapper;


    @Override
    public boolean supports(String state) {
        return StateType.CATEGORY_LIST.toString().equals(state);
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        var command = categoryCommandHandler.createCategoryListCommand(update.message().text());
        String categoryJson = categoryWebClient.findCategoryByTitleAndChatId(chatId, command.command());
        LOGGER.info(String.format("ChatId = %s, Category request = %s", chatId, categoryJson));
        try {
            categoryService.addOrEditCategory(objectMapper.readValue(categoryJson, CategoryRequest.class), chatId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return command.handle(update);
    }
}
