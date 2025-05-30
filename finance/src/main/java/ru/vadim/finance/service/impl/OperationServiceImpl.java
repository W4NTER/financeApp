package ru.vadim.finance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;
    private final CalculateServiceImpl calculateServiceImpl;
    private static final Long MONTHS_TO_BE_LATE = 1L;


    @Override
    @Transactional
    public OperationResponseDTO add(OperationRequestDTO operation) {
        var category = categoryRepository.findById(operation.categoryId())
                .orElseThrow(() -> new EntityNotFoundException(Category.class.getSimpleName()));
        new Thread(() -> calculateServiceImpl.calculateBalance(category.getChat().getChatId())).start();
        calculateServiceImpl.calculateCategoryLimit(category, operation);
        return objectMapper.convertValue(operationRepository.save(
                new Operation(
                        operation.type(),
                        operation.sum(),
                        OffsetDateTime.now(),
                        categoryRepository.findById(operation.categoryId())
                                .orElseThrow(() -> new EntityNotFoundException(Category.class.getSimpleName())),
                        operation.title()
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

    @Override
    public Map<String, Map<String, Integer>> groupOperationsByDateAndType(Long categoryId) {
        List<Operation> operations = operationRepository.findAllByCategoryId(categoryId).stream()
                .filter(operation -> !operation.getCreatedAt()
                        .isBefore(OffsetDateTime.now().minusMonths(MONTHS_TO_BE_LATE)))
                .toList();

        return operations.stream()
                .collect(Collectors.groupingBy(
                        operation -> operation.getCreatedAt().toLocalDate().toString(),
                        Collectors.groupingBy(
                                Operation::getType,
                                Collectors.summingInt(Operation::getSum)
                        )
                ));
    }
}
