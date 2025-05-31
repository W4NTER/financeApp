package ru.vadim.finance.dto.response;

import java.time.LocalDate;

public record BudgetResponse(
        Long id,
        Long chatId,
        Integer sum,
        LocalDate startDate,
        LocalDate endDate
) {
}
