package ru.vadim.finance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.vadim.finance.dto.response.ChatResponseDTO;
import ru.vadim.finance.entity.Chat;
import ru.vadim.finance.exception.EntityAlreadyExistsException;
import ru.vadim.finance.exception.EntityNotFoundException;
import ru.vadim.finance.repository.ChatRepository;
import ru.vadim.finance.service.ChatService;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;

    public ChatServiceImpl(ChatRepository chatRepository, ObjectMapper objectMapper) {
        this.chatRepository = chatRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ChatResponseDTO add(Long chatId) {
        var chat = chatRepository.findByChatId(chatId);
        if (chat.isPresent()) {
            throw new EntityAlreadyExistsException(Chat.class.getSimpleName());
        } else {
            return objectMapper.convertValue(chatRepository.save(
                    new Chat(chatId, OffsetDateTime.now())), ChatResponseDTO.class);
        }
    }

    @Override
    public void delete(Long chatId) {
        var chat = chatRepository.findByChatId(chatId);
        if (chat.isPresent()) {
            chatRepository.delete(chat.get());
        } else {
            throw new EntityNotFoundException(Chat.class.getSimpleName());
        }
    }

    @Override
    public List<ChatResponseDTO> findAll() {
        return chatRepository.findAll().stream()
                .map(chat ->
                        objectMapper.convertValue(chat, ChatResponseDTO.class))
                .toList();
    }

    @Override
    public ChatResponseDTO setStatus(Long chatId, Boolean status) {
        Chat chat = chatRepository.findByChatId(chatId).orElseThrow(
                () -> new EntityNotFoundException(Chat.class.getSimpleName()));
        chat.setEnable(status);
        return objectMapper.convertValue(chatRepository.save(chat), ChatResponseDTO.class);
    }
}
