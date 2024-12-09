package ru.vadim.tgbot.commands.income;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.dto.request.OperationDTO;
import ru.vadim.tgbot.service.CategoryService;
import ru.vadim.tgbot.state.StateType;

import java.util.List;

import static ru.vadim.tgbot.Constants.INCOME_TYPE;
import static ru.vadim.tgbot.Constants.LOGGER;

@Component
@AllArgsConstructor
public class IncomeListCommand implements Command {
    private final CategoryService categoryService;
    private final FinanceAppWebClient financeAppWebClient;
    private final ObjectMapper objectMapper;

    @Override
    public String command() {
        return "Список доходов";
    }

    @Override
    public String description() {
        return "Список дрходов";
    }

    @Override
    public StateType state() {
        return StateType.INCOME_LIST;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        var category = categoryService.findCategoryByChatId(chatId);
        LOGGER.info(String.format("chatId = %s, category of incomes - %s", chatId, category.title()));
        try {
            List<OperationDTO> operations =
                    objectMapper.readValue(financeAppWebClient
                            .findAllOperationsByCategory(category.categoryId()), new TypeReference<>() {});
            LOGGER.info(String.format("chatId = %s, operations: %s", chatId, operations));
            if (operations.isEmpty()) {
                return new SendMessage(chatId, "Вы пока не добавили никаких доходов").replyMarkup(this.menu());
            }
            String request = createRequest(operations);
            return new SendMessage(chatId, request).replyMarkup(this.menu());
        } catch (JsonProcessingException e) {
            LOGGER.info(e.getMessage());
            return new SendMessage(chatId, "Что-то сломалось").replyMarkup(this.menu());
        }
    }

    private String createRequest(List<OperationDTO> operations) {
        StringBuilder builder = new StringBuilder();
        long sum = 0;
        for (OperationDTO operation : operations) {
            if (operation.type().equals(INCOME_TYPE)) {
                builder.append(String.format("%s +%sр\n", operation.title(), operation.sum()));
                sum += operation.sum();
            }
        }
        builder.append("Общая сумма: ").append(sum);
        return builder.toString();
    }
}