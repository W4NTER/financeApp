package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.CategoryWebClient;
import ru.vadim.tgbot.dto.CategoryDTO;
import ru.vadim.tgbot.dto.response.CategoryDto;
import ru.vadim.tgbot.cashService.CashCategoriesService;
import ru.vadim.tgbot.cashService.CurrCategoryCashService;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.Constants.LOGGER;

@Component
public class AddCategoryLimitHandler implements StateHandler {
    private final CashCategoriesService cashCategoriesService;
    private final CategoryWebClient categoryWebClient;
    private final CurrCategoryCashService currCategoryCashService;

    public AddCategoryLimitHandler(
            CashCategoriesService cashCategoriesService,
            CategoryWebClient categoryWebClient,
            CurrCategoryCashService currCategoryCashService
    ) {
        this.cashCategoriesService = cashCategoriesService;
        this.categoryWebClient = categoryWebClient;
        this.currCategoryCashService = currCategoryCashService;
    }

    @Override
    public boolean supports(String state) {
        return StateType.ADD_CATEGORY_LIMIT.toString().equals(state);
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        try {
            CategoryDto currantCategory = currCategoryCashService.getCashCategory(chatId);
            categoryWebClient.setCategoryLimit(
                    chatId,
                    new CategoryDTO(currantCategory.title(), Integer.parseInt(update.message().text())));

            cashCategoriesService.updateCashCategories(chatId);
            return new SendMessage(chatId, "Лимит успешно установлен");
        } catch (Exception e) {
            LOGGER.info("chatId = {}\n{}", chatId, e.getMessage());
            return new SendMessage(chatId, "Не удалось установить лимит");
        }
    }
}
