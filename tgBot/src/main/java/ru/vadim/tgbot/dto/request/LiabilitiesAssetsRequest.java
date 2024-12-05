package ru.vadim.tgbot.dto.request;

public record LiabilitiesAssetsRequest(
        Long chatId,
        String title,
        String type,
        Integer sum
) {
}
