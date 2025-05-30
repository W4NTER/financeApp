package ru.vadim.finance.dto.request;

public record LimitNotificationRequest(
        Long chatId,
        Long categoryId,
        String categoryTitle
) {
}
