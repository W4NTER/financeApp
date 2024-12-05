package ru.vadim.tgbot.state;

import lombok.Getter;

@Getter
public enum StateType {
    MAIN_MENU(new String[]{"Категории", "Баланс", "Детализация"}),
    CATEGORY_LIST(new String[]{}),
    CATEGORY(new String[]{"Добавить категорию", "Расходы", "Доходы", "Список операций"}),
    ADD_CATEGORY(CATEGORY.subMenuItems),
    OUTCOME(new String[]{"Список расходов", "Добавить трату"}),
    OUTCOME_LIST(OUTCOME.subMenuItems),
    ADD_OUTCOME(OUTCOME.subMenuItems),
    INCOME(new String[]{"Список доходов", "Добавить доход"}),
    INCOME_LIST(INCOME.subMenuItems),
    ADD_INCOME(INCOME.subMenuItems),
    OPERATIONS(CATEGORY.subMenuItems),
    BALANCE(new String[]{}),
    ASSETS_LIABILITIES(new String[]{"Графики", "Excel документ"}),
    DIAGRAMS(new String[]{}),
    EXCEL_DOC(new String[]{}),
    ADD_CATEGORY_LIMIT(new String[]{}),
    INCREMENT_BALANCE(BALANCE.subMenuItems),
    DECREMENT_BALANCE(BALANCE.subMenuItems),
    RESET_BALANCE(BALANCE.subMenuItems);

    private final String[] subMenuItems;

    StateType(String[] subMenuItems) {
        this.subMenuItems = subMenuItems;
    }
}
