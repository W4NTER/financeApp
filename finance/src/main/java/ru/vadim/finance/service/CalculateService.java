package ru.vadim.finance.service;

import ru.vadim.finance.dto.request.OperationRequestDTO;
import ru.vadim.finance.entity.Category;

public interface CalculateService {
    void calculateBalance(Long chatId);

    void calculateCategoryLimit(Category category, OperationRequestDTO operation);
}
