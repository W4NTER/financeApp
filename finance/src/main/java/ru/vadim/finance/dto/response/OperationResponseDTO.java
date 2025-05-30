package ru.vadim.finance.dto.response;

import java.time.OffsetDateTime;

public record OperationResponseDTO(
        Long operationId,
        String type,
        Integer sum,
        OffsetDateTime createdAt,
        String title
) {
}
