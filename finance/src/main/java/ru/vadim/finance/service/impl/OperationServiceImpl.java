package ru.vadim.finance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.vadim.finance.dto.request.OperationRequestDTO;
import ru.vadim.finance.dto.response.OperationResponseDTO;
import ru.vadim.finance.entity.Category;
import ru.vadim.finance.entity.Operation;
import ru.vadim.finance.exception.EntityNotFoundException;
import ru.vadim.finance.repository.CategoryRepository;
import ru.vadim.finance.repository.OperationRepository;
import ru.vadim.finance.service.OperationService;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;
    private static final Long MONTHS_TO_BE_LATE = 1L;

    public OperationServiceImpl(
            OperationRepository operationRepository,
            CategoryRepository categoryRepository,
            ObjectMapper objectMapper) {
        this.operationRepository = operationRepository;
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public OperationResponseDTO add(OperationRequestDTO operation) {
        return objectMapper.convertValue(operationRepository.save(
                new Operation(
                        operation.type(),
                        operation.sum(),
                        OffsetDateTime.now(),
                        categoryRepository.findById(operation.categoryId())
                                .orElseThrow(() -> new EntityNotFoundException(Category.class.getSimpleName()))
                )), OperationResponseDTO.class);
    }

    @Override
    public void delete(Long operationId) {
        var operationObj = operationRepository.findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException(Operation.class.getSimpleName()));
        operationRepository.delete(operationObj);
    }

    @Override
    public List<OperationResponseDTO> findAllByTypeAndCategoryId(String type, Long categoryId) {
        return operationRepository.findAllByTypeAndCategoryId(type, categoryId).stream()
                .filter(operation -> !operation.getCreatedAt()
                        .isBefore(OffsetDateTime.now().minusMonths(MONTHS_TO_BE_LATE)))
                .map(operation -> objectMapper.convertValue(operation, OperationResponseDTO.class)).toList();
    }

    @Override
    public List<OperationResponseDTO> findAllByCategoryId(Long categoryId) {
        return operationRepository.findAllByCategoryId(categoryId).stream()
                .filter(operation -> !operation.getCreatedAt()
                        .isBefore(OffsetDateTime.now().minusMonths(MONTHS_TO_BE_LATE)))
                .map(operation -> objectMapper.convertValue(operation, OperationResponseDTO.class)).toList();
    }
}
