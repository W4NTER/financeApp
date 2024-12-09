package ru.vadim.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vadim.tgbot.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select o from Category o where o.chatId = :chat_id")
    Optional<Category> findCategoriesByChatId(@Param("chat_id") Long chatId);
}
