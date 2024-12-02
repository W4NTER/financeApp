package ru.vadim.finance.service;

import ru.vadim.finance.dto.response.ChatResponseDTO;

import java.util.List;

public interface ChatService {
    ChatResponseDTO add(Long chatId);

    void delete(Long chatId);

    List<ChatResponseDTO> findAll();

    ChatResponseDTO setStatus(Long chatId, Boolean status);
}
