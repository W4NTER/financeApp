package ru.vadim.finance.service;

import ru.vadim.finance.dto.request.OperationRequestDTO;
import ru.vadim.finance.dto.response.OperationResponseDTO;

import java.util.List;

public interface OperationService {
    OperationResponseDTO add(OperationRequestDTO operation);

    void delete(Long operationId);

    List<OperationResponseDTO> findAllByTypeAndCategoryId(String type, Long categoryId);

    List<OperationResponseDTO> findAllByCategoryId(Long categoryId);
}
