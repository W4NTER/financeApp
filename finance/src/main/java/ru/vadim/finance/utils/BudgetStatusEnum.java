package ru.vadim.finance.utils;

public enum BudgetStatusEnum {
    ACTIVE("ACTIVE"),
    ARCHIVE("ARCHIVE");

    private String value;

    BudgetStatusEnum(String value) {
        this.value = value;
    }
}
