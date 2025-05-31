package ru.vadim.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vadim.finance.entity.Budget;
import ru.vadim.finance.utils.BudgetStatusEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query("select b from Budget b where b.chat.chatId = :chat_id and b.endDate > :curr_date")
    Optional<Budget> findBudgetByChatIdAndEndDate(
            @Param("chat_id") Long chatId, @Param("curr_date") LocalDate currDate);

    @Query("select b from Budget b where b.status = :status and b.chat.chatId = :chat_id")
    List<Budget> findAllBudgetsByStatusAndChatId(@Param("status") BudgetStatusEnum status,
                                                 @Param("chat_id") Long chatId);
}
