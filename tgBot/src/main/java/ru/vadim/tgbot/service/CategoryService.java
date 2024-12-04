package ru.vadim.tgbot.service;

import ru.vadim.tgbot.dto.request.CategoryRequest;
import ru.vadim.tgbot.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse addOrEditCategory(CategoryRequest category, Long chatId);
    CategoryResponse findCategoryByChatId(Long chatId);
}
