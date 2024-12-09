package ru.vadim.finance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vadim.finance.dto.request.BalanceRequest;
import ru.vadim.finance.dto.response.BalanceResponse;
import ru.vadim.finance.dto.response.OperationResponseDTO;
import ru.vadim.finance.entity.Balance;
import ru.vadim.finance.exception.EntityAlreadyExistsException;
import ru.vadim.finance.exception.EntityNotFoundException;
import ru.vadim.finance.repository.BalanceRepository;
import ru.vadim.finance.service.BalanceService;
import ru.vadim.finance.service.CategoryService;
import ru.vadim.finance.service.OperationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    private final BalanceRepository balanceRepository;
    private final ObjectMapper objectMapper;
    private final CategoryService categoryService;
    private final OperationService operationService;
    private static final Integer DEFAULT_BALANCE = 0;
    private static final String OUTCOME = "OUTCOME";

    @Override
    public BalanceResponse addBalance(Long chatId) {
        var balance = balanceRepository.findBalanceByChatId(chatId);
        if (balance.isPresent()) {
            throw new EntityAlreadyExistsException(Balance.class.getSimpleName());
        } else {
            return objectMapper.convertValue(
                    balanceRepository.save(new Balance(chatId, DEFAULT_BALANCE)), BalanceResponse.class);
        }
    }

    @Override
    @Transactional
    public BalanceResponse incrementBalance(BalanceRequest request) {
        var balanceOpt = balanceRepository.findBalanceByChatId(request.chatId());
        if (balanceOpt.isEmpty()) {
            throw new EntityNotFoundException(Balance.class.getSimpleName());
        }
        var balance = balanceOpt.get();
        balance.setSum(balance.getSum() + request.sum());
        return objectMapper.convertValue(balanceRepository.save(balance), BalanceResponse.class);
    }

    @Override
    public BalanceResponse decrementBalance(BalanceRequest request) {
        var balanceOpt = balanceRepository.findBalanceByChatId(request.chatId());
        if (balanceOpt.isEmpty()) {
            throw new EntityNotFoundException(Balance.class.getSimpleName());
        }
        var balance = balanceOpt.get();
        balance.setSum(balance.getSum() - request.sum());
        return objectMapper.convertValue(balanceRepository.save(balance), BalanceResponse.class);
    }

    @Override
    public BalanceResponse resetBalance(Long chatId) {
        var balanceOpt = balanceRepository.findBalanceByChatId(chatId);
        if (balanceOpt.isEmpty()) {
            throw new EntityNotFoundException(Balance.class.getSimpleName());
        }
        var balance = balanceOpt.get();
        balance.setSum(DEFAULT_BALANCE);
        return objectMapper.convertValue(balanceRepository.save(balance), BalanceResponse.class);
    }

    @Override
    public BalanceResponse findByChatId(Long chatId) {
        var balanceOpt = balanceRepository.findBalanceByChatId(chatId);
        if (balanceOpt.isEmpty()) {
            throw new EntityNotFoundException(Balance.class.getSimpleName());
        }
        return objectMapper.convertValue(balanceOpt.get(), BalanceResponse.class);
    }

    @Override
    public BalanceResponse calculateBalance(Long chatId) {
        var categories = categoryService.findAllByChatId(chatId);
        List<OperationResponseDTO> operations = categories.stream()
                .flatMap(category ->
                        operationService.findAllByCategoryId(category.categoryId()).stream())
                .toList();
        var balance = balanceRepository.findBalanceByChatId(chatId).orElseThrow(() ->
                new EntityNotFoundException(Balance.class.getSimpleName()));
        int newSum = balance.getSum();
        newSum += operations.stream()
                .mapToInt(op -> op.type().equals(OUTCOME) ? -op.sum() : op.sum())
                .sum();
        balance.setSum(newSum);
        return objectMapper.convertValue(balanceRepository.save(balance), BalanceResponse.class);
    }
}
