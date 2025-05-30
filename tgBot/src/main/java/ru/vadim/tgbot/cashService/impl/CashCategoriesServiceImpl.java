package ru.vadim.tgbot.cashService.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.vadim.tgbot.client.CategoryWebClient;
import ru.vadim.tgbot.dto.response.CategoryDto;
import ru.vadim.tgbot.cashService.CashCategoriesService;

import java.time.Duration;
import java.util.List;

import static ru.vadim.tgbot.utils.constants.Constants.LOGGER;

@Service
public class CashCategoriesServiceImpl implements CashCategoriesService {
    private final RedisTemplate<String, List<CategoryDto>> categoryListRedisTemplate;
    private final CategoryWebClient categoryWebClient;
    private final static String FORMAT_FOR_CHAT_CATEGORIES_REDIS_KEY = "categories of chat id: %d";
    private final static Duration TTL_REDIS_DURATION = Duration.ofMinutes(10);
    private final ObjectMapper objectMapper;


    public CashCategoriesServiceImpl(
            RedisTemplate<String, List<CategoryDto>> categoryListRedisTemplate,
            CategoryWebClient categoryWebClient,
            ObjectMapper objectMapper
    ) {
        this.categoryListRedisTemplate = categoryListRedisTemplate;
        this.categoryWebClient = categoryWebClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void setCashCategories(List<CategoryDto> categories, Long chatId) {
        String key = String.format(FORMAT_FOR_CHAT_CATEGORIES_REDIS_KEY, chatId);

        categoryListRedisTemplate.opsForValue().set(key, categories, TTL_REDIS_DURATION);
    }

    @Override
    public List<CategoryDto> getCashCategories(Long chatId) {
        String key = String.format(FORMAT_FOR_CHAT_CATEGORIES_REDIS_KEY, chatId);
        List<CategoryDto> category = categoryListRedisTemplate.opsForValue().get(key);
        LOGGER.info("ChatId = {}, categories from cash - {}", chatId, category);

        return category;
    }

    @Override
    public void updateCashCategories(Long chatId) {
        String key = String.format(FORMAT_FOR_CHAT_CATEGORIES_REDIS_KEY, chatId);
        List<CategoryDto> categories = categoryListRedisTemplate.opsForValue().get(key);

        try {
            if (categories != null) {
                List<CategoryDto> categoriesFromDb =
                        objectMapper.readValue(categoryWebClient.allCategoriesOfChat(chatId),
                                new TypeReference<>() {}
                        );
                categoryListRedisTemplate.opsForValue().set(
                        key,
                        categoriesFromDb,
                        TTL_REDIS_DURATION);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
