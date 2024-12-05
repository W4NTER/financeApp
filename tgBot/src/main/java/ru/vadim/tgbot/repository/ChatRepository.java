package ru.vadim.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.tgbot.entity.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findChatByChatId(Long chatId);
}
