package ru.vadim.tgbot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vadim.tgbot.dto.request.CategoryRequest;
import ru.vadim.tgbot.dto.response.CategoryResponse;
import ru.vadim.tgbot.entity.Category;
import ru.vadim.tgbot.repository.CategoryRepository;
import ru.vadim.tgbot.service.CategoryService;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    @Override
    public CategoryResponse addOrEditCategory(CategoryRequest category, Long chatId) {
        var categoryOpt = categoryRepository.findCategoriesByChatId(chatId);
        if (categoryOpt.isEmpty()) {
            return objectMapper.convertValue(
                    categoryRepository.save(new Category(chatId, category.categoryId(), category.title(), category.categoryLimit())),
            CategoryResponse.class);
        } else {
            var categoryObj = categoryOpt.get();
            categoryObj.setCategoryLimit(category.categoryLimit());
            categoryObj.setTitle(category.title());
            categoryObj.setCategoryId(category.categoryId());
            return objectMapper.convertValue(categoryRepository.save(categoryObj), CategoryResponse.class);
        }
    }

    @Override
    public CategoryResponse findCategoryByChatId(Long chatId) {
        return objectMapper.convertValue(
                categoryRepository.findCategoriesByChatId(chatId),
                CategoryResponse.class);
    }
}
