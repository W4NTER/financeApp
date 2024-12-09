package ru.vadim.finance.service;

import ru.vadim.finance.dto.request.CategoryRequestDTO;
import ru.vadim.finance.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO add(CategoryRequestDTO category);

    void delete(CategoryRequestDTO category);

    List<CategoryResponseDTO> findAllByChatId(Long chatId);

    CategoryResponseDTO setLimit(CategoryRequestDTO category);
}
