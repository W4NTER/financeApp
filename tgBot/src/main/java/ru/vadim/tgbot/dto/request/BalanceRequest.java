package ru.vadim.tgbot.dto.request;

public record BalanceRequest(
        Long chatId,
        Integer sum
) {
}
