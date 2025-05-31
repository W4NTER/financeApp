package ru.vadim.finance.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vadim.finance.dto.request.BudgetRequest;
import ru.vadim.finance.dto.request.UpdateBudgetSumRequest;
import ru.vadim.finance.dto.response.BudgetResponse;
import ru.vadim.finance.entity.Budget;
import ru.vadim.finance.exception.ActiveBudgetNotFoundException;
import ru.vadim.finance.exception.BudgetPeriodConflictException;
import ru.vadim.finance.exception.EntityNotFoundException;
import ru.vadim.finance.repository.BudgetRepository;
import ru.vadim.finance.service.BudgetService;
import ru.vadim.finance.service.ChatService;
import ru.vadim.finance.service.mapper.BudgetMapper;
import ru.vadim.finance.service.mapper.ChatMapper;
import ru.vadim.finance.utils.BudgetStatusEnum;

import java.util.Comparator;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final ChatService chatService;
    private final ChatMapper chatMapper;


    //TODO уточнить, если я не ошибаюсь в аналитике прописано,
    // что изначальная сумма бюджета рассчитывается исходя из лимитов категорий
    public BudgetServiceImpl(
            BudgetRepository budgetRepository,
            BudgetMapper budgetMapper,
            ChatService chatService,
            ChatMapper chatMapper) {
        this.budgetRepository = budgetRepository;
        this.budgetMapper = budgetMapper;
        this.chatService = chatService;
        this.chatMapper = chatMapper;
    }


    //TODO добавить валидацию дат и всех остальных полей
    // (по крайней мере все что зависит от пользователя нужно проверять)
    @Override
    @Transactional
    public BudgetResponse create(BudgetRequest request) {
        List<Budget> activeBudgets = budgetRepository
                .findAllBudgetsByStatusAndChatId(BudgetStatusEnum.ACTIVE, request.chatId());
        if (activeBudgets.isEmpty()) {
            Budget newBudget = budgetMapper.toEntity(
                    request,
                    chatMapper.toEntity(chatService.getByChatId(request.chatId())),
                    BudgetStatusEnum.ACTIVE);
            return budgetMapper.toDto(budgetRepository.save(newBudget));
        } else {
            Budget lastBudget = activeBudgets.stream()
                    .max(Comparator.comparing(Budget::getEndDate)).get();
            if (lastBudget.getEndDate().isAfter(request.startDate())) {
                throw new BudgetPeriodConflictException(lastBudget.getId());
            }
            Budget newBudget = budgetMapper.toEntity(
                    request,
                    chatMapper.toEntity(chatService.getByChatId(request.chatId())),
                    BudgetStatusEnum.ACTIVE);
            return budgetMapper.toDto(budgetRepository.save(newBudget));
        }
    }


    @Override
    @Transactional
    public BudgetResponse incrementBalance(UpdateBudgetSumRequest request) {
        Budget budget = budgetRepository.findById(request.id()).orElseThrow(() ->
                new EntityNotFoundException(Budget.class.getSimpleName(), request.id()));
        budget.setSum(budget.getSum() + Math.abs(request.sum()));

        return budgetMapper.toDto(budgetRepository.save(budget));
    }

    @Override
    @Transactional
    public BudgetResponse decrementBalance(UpdateBudgetSumRequest request) {
        Budget budget = budgetRepository.findById(request.id()).orElseThrow(() ->
                new EntityNotFoundException(Budget.class.getSimpleName(), request.id()));
        budget.setSum(budget.getSum() - Math.abs(request.sum()));

        return budgetMapper.toDto(budgetRepository.save(budget));
    }

    @Override
    @Transactional
    public BudgetResponse archiveBudget(Long id) {
        Budget budget = budgetRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Budget.class.getSimpleName(), id));
        return budgetMapper.toDto(budget);
    }


    @Override
    public List<BudgetResponse> getAllArchived(Long chatId) {
        List<Budget> activeBudgets = budgetRepository.
                findAllBudgetsByStatusAndChatId(BudgetStatusEnum.ACTIVE, chatId);
        if (activeBudgets.isEmpty()) {
            throw new ActiveBudgetNotFoundException();
        }
        return activeBudgets.stream().map(budgetMapper::toDto).toList();
    }


    @Override
    public List<BudgetResponse> getAllActiveBudgetsByChatId(Long chatId) {
        List<Budget> activeBudgets = budgetRepository.
                findAllBudgetsByStatusAndChatId(BudgetStatusEnum.ACTIVE, chatId);
        if (activeBudgets.isEmpty()) {
            throw new ActiveBudgetNotFoundException();
        }
        return activeBudgets.stream().map(budgetMapper::toDto).toList();
    }

}
