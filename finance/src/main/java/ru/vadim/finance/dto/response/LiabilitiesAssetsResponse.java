package ru.vadim.finance.dto.response;

public record LiabilitiesAssetsResponse(
        Long chatId,
        String title,
        String type,
        Integer sum
) {
}
