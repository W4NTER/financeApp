package ru.vadim.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vadim.finance.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.title = :title and c.chat.chatId = :chat_id")
    Optional<Category> findByTitleAndChatId(@Param("title") String title,
                                            @Param("chat_id") Long chatId);

    @Query("select c from Category c where c.chat.chatId = :chat_id")
    List<Category> findAllByChatId(@Param("chat_id") Long chatId);
}
