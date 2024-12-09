package ru.vadim.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vadim.finance.entity.LiabilitiesAssets;

import java.util.List;
import java.util.Optional;

public interface LiabilitiesAssetsRepository extends JpaRepository<LiabilitiesAssets, Long> {
    @Query("select o from LiabilitiesAssets o where o.chat.chatId = :chat_id")
    List<LiabilitiesAssets> findAllByChatId(@Param("chat_id") Long chatId);

    @Query("select o from LiabilitiesAssets o where o.chat.chatId = :chat_id and o.title = :title")
    Optional<LiabilitiesAssets> findByTitleAndChatId(@Param("title") String title, @Param("chat_id") Long chatId);

    @Modifying
    @Query("delete from LiabilitiesAssets o where o.chat.chatId = :chat_id")
    void deleteAllByChatId(@Param("chat_id") Long chatId);
}
