package ru.vadim.finance.exception;

public class ActiveBudgetNotFoundException extends CustomException {
    public ActiveBudgetNotFoundException() {
        super(ActiveBudgetNotFoundException.class, "No active budget found for the specified chat");
    }
}
