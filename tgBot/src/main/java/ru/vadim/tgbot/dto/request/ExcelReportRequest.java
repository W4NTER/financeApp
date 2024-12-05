package ru.vadim.tgbot.dto.request;

public record ExcelReportRequest(
        Long chatId,
        byte[] data
) {
}
