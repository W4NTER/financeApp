package ru.vadim.tgbot.dto.request;

public record LimitNotificationRequest(
        Long chatId,
        Long categoryId,
        String categoryTitle
) {
}
