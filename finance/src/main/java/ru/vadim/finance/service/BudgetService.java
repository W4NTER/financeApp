package ru.vadim.finance.service;

import ru.vadim.finance.dto.request.BudgetRequest;
import ru.vadim.finance.dto.request.UpdateBudgetSumRequest;
import ru.vadim.finance.dto.response.BudgetResponse;

import java.util.List;

public interface BudgetService {
    BudgetResponse create(BudgetRequest request);

    BudgetResponse incrementBalance(UpdateBudgetSumRequest request);

    BudgetResponse decrementBalance(UpdateBudgetSumRequest request);

    BudgetResponse archiveBudget(Long id);

    List<BudgetResponse> getAllActiveBudgetsByChatId(Long chatId);

    List<BudgetResponse> getAllArchived(Long chatId);
}
