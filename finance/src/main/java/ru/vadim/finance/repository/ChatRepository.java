package ru.vadim.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.finance.entity.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChatId(Long chatId);
}
