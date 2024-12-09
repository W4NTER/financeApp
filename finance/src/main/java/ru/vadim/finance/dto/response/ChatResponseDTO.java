package ru.vadim.finance.dto.response;

import java.time.OffsetDateTime;

public record ChatResponseDTO(
        Long chatId,
        OffsetDateTime createdAt,
        Boolean enable
) {
}
