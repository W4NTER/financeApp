package ru.vadim.tgbot.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vadim.tgbot.entity.Chat;
import ru.vadim.tgbot.repository.ChatRepository;
import ru.vadim.tgbot.service.ChatService;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.Constants.LOGGER;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    //По сути здесь не может быть не найден chat_id, если чат зарегистрирован
    @Override
    public Chat findOrCreateChat(Long chatId) {
        var chat = chatRepository.findChatByChatId(chatId);
        if (chat.isPresent()) {
            LOGGER.info("chat exists");
            return chat.get();
        } else {
            LOGGER.info("new chat created");
            return chatRepository.save(new Chat(chatId, StateType.MAIN_MENU.toString()));
        }
    }

    @Override
    @Transactional
    public String setState(Long chatId, StateType state) {
        Chat chatObj = chatRepository.findChatByChatId(chatId).get();
        chatObj.setState(state.toString());
        LOGGER.info(String.format("set state to %s", state));
        return chatRepository.save(chatObj).getState();
    }
}
