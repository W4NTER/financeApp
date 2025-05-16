package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.cashService.CurrCategoryCashService;
import ru.vadim.tgbot.client.CategoryWebClient;
import ru.vadim.tgbot.commands.category.CategoryCommand;
import ru.vadim.tgbot.dto.response.CategoryDto;
import ru.vadim.tgbot.state.StateType;

@Component
public class CategoryListHandler implements StateHandler {
    private final CurrCategoryCashService currCategoryCashService;
    private final CategoryWebClient categoryWebClient;
    private final ObjectMapper objectMapper;
    private final static Logger LOGGER = LogManager.getLogger();

    public CategoryListHandler(
            CurrCategoryCashService currCategoryCashService,
            CategoryWebClient categoryWebClient,
            ObjectMapper objectMapper) {
        this.currCategoryCashService = currCategoryCashService;
        this.categoryWebClient = categoryWebClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(String state) {
        return StateType.CATEGORY_LIST.toString().equals(state);
    }

    //TODO подумать как переписать в будущем,чтобы не кидать запрос на другой сервис, а брать все в кэше
    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        try {
            currCategoryCashService.setCashCategory(
                    chatId,
                    objectMapper.readValue(
                            categoryWebClient.findCategoryByTitleAndChatId(chatId, update.message().text()),
                            CategoryDto.class
                    ));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getStackTrace());
        }
        return new CategoryCommand(update.message().text()).handle(update);
    }
}
