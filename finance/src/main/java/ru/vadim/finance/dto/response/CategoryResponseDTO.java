package ru.vadim.finance.dto.response;

import java.time.OffsetDateTime;

public record CategoryResponseDTO(
        Long categoryId,
        String title,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Integer limit
) {
}
