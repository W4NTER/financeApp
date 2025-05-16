package ru.vadim.tgbot.dto.response;

public record CategoryDto(
        Long categoryId,
        String title,
        Integer categoryLimit
) {
}
