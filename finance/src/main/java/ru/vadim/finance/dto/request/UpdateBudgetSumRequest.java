package ru.vadim.finance.dto.request;

public record UpdateBudgetSumRequest(
        Long id,
        Integer sum
) {
}
