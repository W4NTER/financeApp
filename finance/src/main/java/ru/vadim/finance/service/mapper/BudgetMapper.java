package ru.vadim.finance.service.mapper;

import org.springframework.stereotype.Component;
import ru.vadim.finance.dto.request.BudgetRequest;
import ru.vadim.finance.dto.response.BudgetResponse;
import ru.vadim.finance.entity.Budget;
import ru.vadim.finance.entity.Chat;
import ru.vadim.finance.utils.BudgetStatusEnum;

@Component
public class BudgetMapper {

    public BudgetResponse toDto(Budget budget) {
        return new BudgetResponse(
                budget.getId(),
                budget.getChat().getChatId(),
                budget.getSum(),
                budget.getStartDate(),
                budget.getEndDate()
        );
    }

    public Budget toEntity(BudgetRequest request, Chat chat, BudgetStatusEnum status) {
        return new Budget(
                request.sum(),
                request.startDate(),
                request.endDate(),
                chat,
                status
        );
    }
}
