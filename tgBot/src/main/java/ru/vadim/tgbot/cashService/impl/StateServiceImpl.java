package ru.vadim.tgbot.cashService.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.vadim.tgbot.cashService.StateService;
import ru.vadim.tgbot.utils.state.StateType;

import java.time.Duration;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {
    private final RedisTemplate<String, String> redisTemplate;
    private final static String FORMAT_FOR_CHAT_STATE_REDIS_KEY = "chat: %d";
    private final static Duration TTL_DURATION = Duration.ofMinutes(10);
    private final static Logger LOGGER  = LogManager.getLogger();

    public StateServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //По сути здесь не может быть не найден chat_id, если чат зарегистрирован
    @Override
    public String getState(Long chatId) {
        String key = String.format(FORMAT_FOR_CHAT_STATE_REDIS_KEY, chatId);
        Optional<String> state = Optional.ofNullable(redisTemplate.opsForValue().get(key));

        LOGGER.info("chat {}, has state = {}", chatId, state);
        return state.orElseGet(StateType.MAIN_MENU::toString);
    }

    @Override
    public void setState(Long chatId, StateType state) {
        String key = String.format(FORMAT_FOR_CHAT_STATE_REDIS_KEY, chatId);
        redisTemplate.opsForValue().set(key, state.toString(), TTL_DURATION);
    }
}
