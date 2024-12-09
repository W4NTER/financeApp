package ru.vadim.tgbot.dto.response;

public record CategoryResponse(
        Long chatId,
        Long categoryId,
        String title,
        Integer categoryLimit
) {
}
