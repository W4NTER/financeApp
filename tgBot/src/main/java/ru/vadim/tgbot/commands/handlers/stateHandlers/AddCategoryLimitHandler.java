package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.CategoryWebClient;
import ru.vadim.tgbot.dto.CategoryDTO;
import ru.vadim.tgbot.service.CategoryService;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.Constants.LOGGER;

@Component
@AllArgsConstructor
public class AddCategoryLimitHandler implements StateHandler {
    private final CategoryService categoryService;
    private final CategoryWebClient categoryWebClient;
    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(String state) {
        return StateType.ADD_CATEGORY_LIMIT.toString().equals(state);
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        try {
            var categoryState = categoryService.findCategoryByChatId(chatId);
            var categoryJson = categoryWebClient.findCategoryByTitleAndChatId(chatId, categoryState.title());
            CategoryDTO categoryDTO = objectMapper.readValue(categoryJson, CategoryDTO.class);
            categoryWebClient.setCategoryLimit(
                    chatId,
                    new CategoryDTO(categoryDTO.title(), Integer.parseInt(update.message().text())));
            return new SendMessage(chatId, "Лимит успешно установлен");
        } catch (Exception e) {
            LOGGER.info(String.format("chatId = %d\n%s", chatId, e.getMessage()));
            return new SendMessage(chatId, "Не удалось установить лимит");
        }
    }
}
