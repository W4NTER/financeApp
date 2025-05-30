package ru.vadim.finance.dto.request;

public record OperationRequestDTO(
        String type,
        Integer sum,
        Long categoryId,
        String title
) {
}
