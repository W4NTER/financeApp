package ru.vadim.tgbot.cashService.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.vadim.tgbot.cashService.CurrCategoryCashService;
import ru.vadim.tgbot.cashService.StateService;
import ru.vadim.tgbot.dto.response.CategoryDto;
import ru.vadim.tgbot.utils.state.StateType;

import java.time.Duration;

@Service
public class CurrCategoryCashServiceImpl implements CurrCategoryCashService {
    private final RedisTemplate<String, CategoryDto> categoryRedisTemplate;
    private final static String FORMAT_FOR_CURR_CATEGORY_REDIS_KEY = "curr category of chat: %s";
    private final static Duration DURATION_FOR_CURR_CATEGORY = Duration.ofMinutes(20);
    private final StateService stateService;
    private final static Logger LOGGER = LogManager.getLogger();

    public CurrCategoryCashServiceImpl(RedisTemplate<String, CategoryDto> categoryRedisTemplate,
                                       StateService stateService) {
        this.categoryRedisTemplate = categoryRedisTemplate;
        this.stateService = stateService;
    }

    @Override
    public void setCashCategory(Long chatId, CategoryDto categoryDto) {
        String key = String.format(FORMAT_FOR_CURR_CATEGORY_REDIS_KEY, chatId);
        categoryRedisTemplate.opsForValue().set(key, categoryDto, DURATION_FOR_CURR_CATEGORY);
    }

    @Override
    public CategoryDto getCashCategory(Long chatId) {
        String key = String.format(FORMAT_FOR_CURR_CATEGORY_REDIS_KEY, chatId);
        CategoryDto categoryDto = categoryRedisTemplate.opsForValue().get(key);
        LOGGER.info("called curr category cash service");
        if (categoryDto == null) {
            stateService.setState(chatId, StateType.MAIN_MENU);
            //TODO сделать нормальную обработку ошибок и возвращать пользователя на main_menu,
            // если уже стерлись данные о его текущей категории
            throw new RuntimeException("В Редисе нет текущей категории");
        }
        return categoryDto;
    }
}
