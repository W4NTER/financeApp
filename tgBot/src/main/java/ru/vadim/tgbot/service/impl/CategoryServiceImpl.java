package ru.vadim.tgbot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vadim.tgbot.dto.request.CategoryRequest;
import ru.vadim.tgbot.dto.response.CategoryResponse;
import ru.vadim.tgbot.entity.Category;
import ru.vadim.tgbot.repository.CategoryRepository;
import ru.vadim.tgbot.service.CategoryService;
import static ru.vadim.tgbot.constants.Constants.*;

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
                    categoryRepository.save(
                            new Category(
                                    chatId,
                                    category.categoryId(),
                                    category.title(),
                                    category.categoryLimit())),
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
        var category =  categoryRepository.findCategoriesByChatId(chatId);
        LOGGER.info(String.format("ChatId = %s, category - %s", chatId, category.get().getTitle()));
        return objectMapper.convertValue(category, CategoryResponse.class);
    }
}
