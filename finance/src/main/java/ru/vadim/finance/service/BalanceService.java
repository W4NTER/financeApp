package ru.vadim.finance.service;

import ru.vadim.finance.dto.request.BalanceRequest;
import ru.vadim.finance.dto.response.BalanceResponse;

public interface BalanceService {
    BalanceResponse addBalance(Long chatId);

    BalanceResponse incrementBalance(BalanceRequest request);

    BalanceResponse decrementBalance(BalanceRequest request);

    BalanceResponse resetBalance(Long chatId);

    BalanceResponse findByChatId(Long chatId);

    BalanceResponse calculateBalance(Long chatId);
}
