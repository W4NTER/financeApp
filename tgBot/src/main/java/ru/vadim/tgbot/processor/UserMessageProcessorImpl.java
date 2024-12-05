package ru.vadim.tgbot.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.commands.handlers.CategoryCommandHandler;
import ru.vadim.tgbot.dto.CategoryDTO;
import ru.vadim.tgbot.dto.request.BalanceRequest;
import ru.vadim.tgbot.dto.request.CategoryRequest;
import ru.vadim.tgbot.dto.request.OperationDTO;
import ru.vadim.tgbot.dto.response.CategoryResponse;
import ru.vadim.tgbot.entity.Chat;
import ru.vadim.tgbot.service.CategoryService;
import ru.vadim.tgbot.service.ChatService;

import java.util.List;
import java.util.Optional;

import static ru.vadim.tgbot.Constants.LOGGER;

@Component
@AllArgsConstructor
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final List<Command> commands;
    private final ChatService chatService;
    private final FinanceAppWebClient financeAppWebClient;
    private final CategoryCommandHandler categoryCommandHandler;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;
    private final static String OUTCOME = "OUTCOME";
    private final static String INCOME = "INCOME";

    @Override
    public SendMessage process(Update update) {
        var command = getCommand(update);
        Long chatId = update.message().chat().id();
        Chat chat = chatService.findOrCreateChat(chatId);
        if (command.isEmpty()) {
            String state = chat.getState();
            LOGGER.info(state);
            return commandToServer(state, update);
        }
        LOGGER.info(command.get());
        chatService.setState(
                update.message().chat().id(),
                command.get().state());
        LOGGER.info("process the command");
        return command.get().handle(update);
    }

    private SendMessage commandToServer(String state, Update update) {
        Long chatId = update.message().chat().id();
        switch (state) {
            case "ADD_CATEGORY" -> {
                return addCategory(update, chatId);
            }
            case "ADD_OUTCOME" -> {
                return addOutcome(update, chatId);
            }
            case "ADD_INCOME" -> {
                return addIncome(update, chatId);
            }
            case "CATEGORY_LIST" -> {
                return goToCategory(update, chatId);
            }
            case "ADD_CATEGORY_LIMIT" -> {
                return addCategoryLimit(update, chatId);
            }
            case "INCREMENT_BALANCE" -> {
                return incrementBalance(update, chatId);
            }
            case "DECREMENT_BALANCE" -> {
                return decrementBalance(update, chatId);
            }
            default -> {
                LOGGER.info("no command find");
                return new SendMessage(update.message().chat().id(), "Такой команды не существует");
            }
        }
    }

    private SendMessage decrementBalance(Update update, Long chatId) {
        try {
            financeAppWebClient.decrementBalance(
                    new BalanceRequest(chatId, Integer.parseInt(update.message().text())));
            return new SendMessage(chatId, "Все записал, можно двигаться дальше");
        } catch (Exception e) {
            return new SendMessage(chatId, "Что-то пошло не так");
        }
    }

    private SendMessage incrementBalance(Update update, Long chatId) {
        try {
            financeAppWebClient.incrementBalance(
                    new BalanceRequest(chatId, Integer.parseInt(update.message().text())));
            return new SendMessage(chatId, "Все записал, можно двигаться дальше");
        } catch (Exception e) {
            return new SendMessage(chatId, "Что-то пошло не так");
        }
    }

    private SendMessage addCategoryLimit(Update update, Long chatId) {
        try {
            var categoryState = categoryService.findCategoryByChatId(chatId);
            var categoryJson = financeAppWebClient.findCategoryByTitleAndChatId(chatId, categoryState.title());
            CategoryDTO categoryDTO = objectMapper.readValue(categoryJson, CategoryDTO.class);
            financeAppWebClient.setLimit(chatId, new CategoryDTO(categoryDTO.title(), Integer.parseInt(update.message().text())));
            return new SendMessage(chatId, "Лимит успешно установлен");
        } catch (Exception e) {
            LOGGER.info(String.format("chatId = %d\n%s", chatId, e.getMessage()));
            return new SendMessage(chatId, "Не удалось установить лимит");
        }
    }

    private SendMessage goToCategory(Update update, Long chatId) {
        var command = categoryCommandHandler.createCategoryListCommand(update.message().text());
        String categoryJson = financeAppWebClient.findCategoryByTitleAndChatId(chatId, command.command());
        LOGGER.info(String.format("ChatId = %s, Category request = %s", chatId, categoryJson));
        try {
            categoryService.addOrEditCategory(objectMapper.readValue(categoryJson, CategoryRequest.class), chatId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return command.handle(update);
    }

    private SendMessage addIncome(Update update, Long chatId) {
        var category = categoryService.findCategoryByChatId(chatId);
        String[] message = update.message().text().split("-");
        LOGGER.info(String.format("chatId = %s, Add income message - %s", chatId, update.message().text()));
        return getSendMessage(chatId, category, message, INCOME);
    }

    private SendMessage addOutcome(Update update, Long chatId) {
        var category = categoryService.findCategoryByChatId(chatId);
        LOGGER.info(String.format("chatId = %s, Add income message - %s", chatId, update.message().text()));
        String[] message = update.message().text().split("-");
        return getSendMessage(chatId, category, message, OUTCOME);

    }

    @NotNull
    private SendMessage getSendMessage(Long chatId, CategoryResponse category, String[] message, String outcome) {
        try {
            int sum = Integer.parseInt(message[1].trim());
            financeAppWebClient.addOperation(
                    new OperationDTO(outcome, sum, category.categoryId(), message[0]));
            return new SendMessage(chatId, "Все записал, можно двигаться дальше");
        } catch (Exception e) {
            LOGGER.info(String.format("ChatId = %d\n%s", chatId, e.getMessage()));
            return new SendMessage(chatId, "Неверный формат данных");
        }
    }

    private SendMessage addCategory(Update update, Long chatId) {
        financeAppWebClient.addCategory(chatId, update.message().text());
        return new SendMessage(chatId, "Все записал, можно двигаться дальше");
    }

    private Optional<? extends Command> getCommand(Update update) {
        return commands.stream().filter(cmd ->
                cmd.command().equals(update.message().text())).findFirst();
    }
}
