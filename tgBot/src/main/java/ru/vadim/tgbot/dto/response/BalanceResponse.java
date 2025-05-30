package ru.vadim.tgbot.dto.response;

public record BalanceResponse(
        Long chatId,
        Integer sum
) {
}
