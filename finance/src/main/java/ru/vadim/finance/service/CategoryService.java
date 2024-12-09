package ru.vadim.finance.service;

import ru.vadim.finance.dto.request.CategoryRequestDTO;
import ru.vadim.finance.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO add(CategoryRequestDTO category, Long chatId);

    void delete(CategoryRequestDTO category, Long chatId);

    List<CategoryResponseDTO> findAllByChatId(Long chatId);

    CategoryResponseDTO setLimit(CategoryRequestDTO category, Long chatId);

    CategoryResponseDTO findCategoryByTitleAndChatId(String title, Long chatId);
}
