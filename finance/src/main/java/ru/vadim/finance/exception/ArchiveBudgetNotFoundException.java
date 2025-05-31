package ru.vadim.finance.exception;

public class ArchiveBudgetNotFoundException extends CustomException {
    public ArchiveBudgetNotFoundException() {
        super(ArchiveBudgetNotFoundException.class, "No archive budget found for the specified chat");
    }
}
