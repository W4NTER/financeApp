package ru.vadim.finance.dto.request;

import java.time.LocalDate;

public record BudgetRequest(
        Long chatId,
        Integer sum,
        LocalDate startDate,
        LocalDate endDate) {
}
