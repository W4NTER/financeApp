package ru.vadim.finance.dto.request;

public record ExcelReportRequest(
        Long chatId,
        byte[] data
) {
}
