package ru.vadim.finance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vadim.finance.client.BotClient;
import ru.vadim.finance.dto.request.LimitNotificationRequest;
import ru.vadim.finance.dto.request.OperationRequestDTO;
import ru.vadim.finance.dto.response.OperationResponseDTO;
import ru.vadim.finance.entity.Balance;
import ru.vadim.finance.entity.Category;
import ru.vadim.finance.exception.EntityNotFoundException;
import ru.vadim.finance.repository.BalanceRepository;
import ru.vadim.finance.repository.CategoryRepository;
import ru.vadim.finance.repository.OperationRepository;
import ru.vadim.finance.service.CategoryService;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CalculateServiceImpl {
    private CategoryService categoryService;
    private OperationRepository operationRepository;
    private BalanceRepository balanceRepository;
    private BotClient botClient;
    private CategoryRepository categoryRepository;
    private ObjectMapper objectMapper;
    private static final String OPERATION_TYPE = "OUTCOME";
    private static final Long MONTHS_TO_BE_LATE = 1L;

    public void calculateBalance(Long chatId) {
        var categories = categoryService.findAllByChatId(chatId);
        List<OperationResponseDTO> operations = categories.stream()
                .flatMap(category ->
                        operationRepository.findAllByCategoryId(category.categoryId()).stream()
                                .filter(operation -> !operation.getCreatedAt()
                                        .isBefore(OffsetDateTime.now().minusMonths(MONTHS_TO_BE_LATE)))
                                .map(operation ->
                                        objectMapper
                                                .convertValue(operation, OperationResponseDTO.class)))
                .toList();
        var balance = balanceRepository.findBalanceByChatId(chatId).orElseThrow(() ->
                new EntityNotFoundException(Balance.class.getSimpleName()));
        int newSum = balance.getSum();
        newSum += operations.stream()
                .mapToInt(op -> op.type().equals(OPERATION_TYPE) ? -op.sum() : op.sum())
                .sum();
        balance.setSum(newSum);
        balanceRepository.save(balance);
    }

    public void calculateCategoryLimit(Category category, OperationRequestDTO operation) {
        var limit = category.getLimit();
        if (limit != null && limit != 0) {
            if (operation.type().equals(OPERATION_TYPE)) {
                limit -= operation.sum();
            } else {
                limit += operation.sum();
            }
            if (limit < 0) {
                limit = 0;
                botClient.sendLimitNotification(
                        new LimitNotificationRequest(
                                category.getChat().getChatId(),
                                category.getCategoryId(),
                                category.getTitle()
                        )
                );
                category.setLimit(limit);
                categoryRepository.save(category);
            }
        }
    }
}
