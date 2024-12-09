package ru.vadim.finance.dto.request;

import java.time.OffsetDateTime;

public record CategoryRequestDTO(
        String title,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long chatId,
        Integer limit
) {

}
