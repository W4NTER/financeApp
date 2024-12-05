package ru.vadim.finance.dto.request;

public record ChartRequest(
        Long chatId,
        byte[] chart
) {
}
