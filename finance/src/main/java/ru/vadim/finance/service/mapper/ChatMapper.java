package ru.vadim.finance.service.mapper;

import org.springframework.stereotype.Component;
import ru.vadim.finance.dto.response.ChatResponseDTO;
import ru.vadim.finance.entity.Chat;

@Component
public class ChatMapper {

    public ChatResponseDTO toDto(Chat chat) {
        return new ChatResponseDTO(
                chat.getChatId(),
                chat.getCreatedAt(),
                chat.getEnable()
        );
    }

    public Chat toEntity(ChatResponseDTO dto) {
        return new Chat(
                dto.chatId(),
                dto.createdAt(),
                dto.enable()
        );
    }

}
