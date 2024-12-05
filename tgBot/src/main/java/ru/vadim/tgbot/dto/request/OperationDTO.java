package ru.vadim.tgbot.dto.request;

public record OperationDTO(
        String type,
        Integer sum,
        Long categoryId,
        String title
) {
}
