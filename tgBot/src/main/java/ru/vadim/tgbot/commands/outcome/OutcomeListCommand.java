package ru.vadim.tgbot.commands.outcome;

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

import static ru.vadim.tgbot.Constants.LOGGER;
import static ru.vadim.tgbot.Constants.OUTCOME_TYPE;

@Component
@AllArgsConstructor
public class OutcomeListCommand implements Command {
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;
    private final FinanceAppWebClient financeAppWebClient;

    @Override
    public String command() {
        return "Список расходов";
    }

    @Override
    public String description() {
        return "Список расходов";
    }

    @Override
    public StateType state() {
        return StateType.OUTCOME_LIST;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        var category = categoryService.findCategoryByChatId(chatId);
        LOGGER.info(String.format("chatId = %s, category of outcomes - %s", chatId, category.title()));
        try {
            List<OperationDTO> operations =
                    objectMapper.readValue(financeAppWebClient
                            .findAllOperationsByCategory(category.categoryId()), new TypeReference<>() {});
            LOGGER.info(String.format("chatId = %s, operations: %s", chatId, operations));
            if (operations.isEmpty()) {
                return new SendMessage(chatId, "Вы пока не добавили никаких расходов").replyMarkup(this.menu());
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
            if (operation.type().equals(OUTCOME_TYPE)) {
                builder.append(String.format("%s -%sр\n", operation.title(), operation.sum()));
                sum -= operation.sum();
            }
        }
        builder.append("Общая сумма: ").append(sum);
        return builder.toString();
    }
}