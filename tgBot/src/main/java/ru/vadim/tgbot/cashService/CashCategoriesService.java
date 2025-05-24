package ru.vadim.tgbot.cashService;

import ru.vadim.tgbot.dto.response.CategoryDto;

import java.util.List;

public interface CashCategoriesService {
    void setCashCategories(List<CategoryDto> categories, Long chatId);

    List<CategoryDto> getCashCategories(Long chatId);

    void updateCashCategories(Long chatId);
}
