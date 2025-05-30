package ru.vadim.tgbot.cashService;

import ru.vadim.tgbot.dto.response.CategoryDto;

public interface CurrCategoryCashService {
    void setCashCategory(Long chatId, CategoryDto categoryDto);

    CategoryDto getCashCategory(Long chatId);
}
