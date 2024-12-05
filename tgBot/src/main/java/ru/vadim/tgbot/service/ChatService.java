package ru.vadim.tgbot.service;

import ru.vadim.tgbot.entity.Chat;
import ru.vadim.tgbot.state.StateType;

public interface ChatService {
    Chat findOrCreateChat(Long chatId);

    String setState(Long chatId, StateType state);
}
