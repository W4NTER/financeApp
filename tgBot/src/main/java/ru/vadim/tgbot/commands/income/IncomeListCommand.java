package ru.vadim.tgbot.commands.income;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.OperationsWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.dto.request.OperationDTO;
import ru.vadim.tgbot.dto.response.CategoryDto;
import ru.vadim.tgbot.cashService.CurrCategoryCashService;
import ru.vadim.tgbot.utils.state.StateType;

import java.util.List;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.INCOME_LIST_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.INCOME_LIST_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.utils.constants.Constants.INCOME_TYPE;
import static ru.vadim.tgbot.utils.constants.Constants.LOGGER;

@Component
public class IncomeListCommand implements Command<SendMessage, SendResponse> {
    private final OperationsWebClient operationsWebClient;
    private final ObjectMapper objectMapper;
    private final CurrCategoryCashService currCategoryCashService;

    public IncomeListCommand(
            OperationsWebClient operationsWebClient,
            ObjectMapper objectMapper,
            CurrCategoryCashService currCategoryCashService) {
        this.operationsWebClient = operationsWebClient;
        this.objectMapper = objectMapper;
        this.currCategoryCashService = currCategoryCashService;
    }

    @Override
    public String command() {
        return INCOME_LIST_COMMAND;
    }

    @Override
    public String description() {
        return INCOME_LIST_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.INCOME_LIST;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        CategoryDto category = currCategoryCashService.getCashCategory(chatId);
        LOGGER.info(String.format("chatId = %s, category of incomes - %s", chatId, category.title()));
        try {
            List<OperationDTO> operations =
                    objectMapper.readValue(operationsWebClient
                            .findAllOperationsByCategory(category.categoryId()), new TypeReference<>() {});
            LOGGER.info(String.format("chatId = %s, operations: %s", chatId, operations));
            if (operations.isEmpty()) {
                return new SendMessage(chatId, "Вы пока не добавили никаких доходов").replyMarkup(menu());
            }
            String request = createRequest(operations);
            return new SendMessage(chatId, request).replyMarkup(menu());
        } catch (JsonProcessingException e) {
            LOGGER.info(e.getMessage());
            return new SendMessage(chatId, "Что-то сломалось").replyMarkup(menu());
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
