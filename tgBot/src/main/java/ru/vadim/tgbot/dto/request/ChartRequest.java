package ru.vadim.tgbot.dto.request;

public record ChartRequest(
        Long chatId,
        byte[] chart
) {
}
