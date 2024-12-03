package ru.vadim.tgbot.service;

import ru.vadim.tgbot.entity.Chat;
import ru.vadim.tgbot.state.StateType;

public interface ChatService {
    Chat findOrCreateChat(Long chatId);

    Chat setState(Chat chat, StateType state);
}
