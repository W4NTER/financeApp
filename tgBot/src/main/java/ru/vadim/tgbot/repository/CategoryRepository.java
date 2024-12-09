package ru.vadim.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.tgbot.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoriesByChatId(Long chatId);
}
