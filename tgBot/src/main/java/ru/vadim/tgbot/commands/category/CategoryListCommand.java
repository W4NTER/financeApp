package ru.vadim.tgbot.commands.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.dto.CategoryDTO;
import ru.vadim.tgbot.state.StateType;

import java.util.List;

import static ru.vadim.tgbot.Constants.MAIN_MENU_ARR;
import static ru.vadim.tgbot.Constants.RESIZE_KEYBOARD;

@Component
public class CategoryListCommand implements Command {
    private final FinanceAppWebClient financeAppWebClient;
    private final ObjectMapper objectMapper;
    private KeyboardButton[][] buttons;

    public CategoryListCommand(FinanceAppWebClient financeAppWebClient, ObjectMapper objectMapper) {
        this.financeAppWebClient = financeAppWebClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public String command() {
        return "Категории";
    }

    @Override
    public String description() {
        return "Выберите категорию или добавьте новую";
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
                    financeAppWebClient.allCategoriesOfChat(update.message().chat().id()),
                    new TypeReference<>() {});
            buttons = categories.stream()
                    .map(category -> new KeyboardButton[]{new KeyboardButton(category.title())})
                    .toArray(KeyboardButton[][]::new);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Command.super.handle(update);
    }
}
