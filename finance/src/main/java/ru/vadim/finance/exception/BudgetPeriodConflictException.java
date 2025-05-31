package ru.vadim.finance.exception;

public class BudgetPeriodConflictException extends CustomException {
    public BudgetPeriodConflictException(Long id) {
        super(ru.vadim.finance.exception.EntityAlreadyExistsException.class,
                String.format("Budgets periods conflict id of conflicted budget = %d", id));
    }
}
