package ru.vadim.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.finance.entity.Balance;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findBalanceByChatId(Long chatId);
}
