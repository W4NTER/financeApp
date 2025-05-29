package ru.vadim.tgbot.commands.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.CategoryWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.dto.CategoryDTO;
import ru.vadim.tgbot.utils.state.StateType;

import java.util.List;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.CATEGORY_LIST_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.CATEGORY_LIST_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.utils.constants.Constants.MAIN_MENU_ARR;
import static ru.vadim.tgbot.utils.constants.Constants.RESIZE_KEYBOARD;

@Component
public class CategoryListCommand implements Command<SendMessage, SendResponse> {
    private final CategoryWebClient categoryWebClient;
    private final ObjectMapper objectMapper;
    private KeyboardButton[][] buttons;

    public CategoryListCommand(CategoryWebClient categoryWebClient, ObjectMapper objectMapper) {
        this.categoryWebClient = categoryWebClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public String command() {
        return CATEGORY_LIST_COMMAND;
    }

    @Override
    public String description() {
        return CATEGORY_LIST_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.CATEGORY_LIST;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        var keyboard = new ReplyKeyboardMarkup(buttons)
                .resizeKeyboard(RESIZE_KEYBOARD)
                .oneTimeKeyboard(false);
        keyboard.addRow(state().getSubMenuItems());
        keyboard.addRow(MAIN_MENU_ARR);
        return keyboard;
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            List<CategoryDTO> categories = objectMapper.readValue(
                    categoryWebClient.allCategoriesOfChat(update.message().chat().id()),
                    new TypeReference<>() {});
            buttons = categories.stream()
                    .map(category -> new KeyboardButton[]{new KeyboardButton(category.title())})
                    .toArray(KeyboardButton[][]::new);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new SendMessage(update.message().chat().id(), post());
    }
}
