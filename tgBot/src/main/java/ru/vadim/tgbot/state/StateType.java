package ru.vadim.tgbot.state;

import lombok.Getter;

@Getter
public enum StateType {
    MAIN_MENU(new String[]{"Категории", "Баланс", "Детализация"}),
    CATEGORY_LIST(new String[]{"Добавить категорию"}),
    CATEGORY(new String[]{"Расходы", "Доходы", "Список операций"}),
    ADD_CATEGORY(CATEGORY.subMenuItems),
    OUTCOME(new String[]{"Список расходов", "Добавить трату"}),
    OUTCOME_LIST(OUTCOME.subMenuItems),
    ADD_OUTCOME(OUTCOME.subMenuItems),
    INCOME(new String[]{"Список доходов", "Добавить доход"}),
    INCOME_LIST(INCOME.subMenuItems),
    ADD_INCOME(INCOME.subMenuItems),
    OPERATIONS(CATEGORY.subMenuItems),
    BALANCE(new String[]{}),
    DIAGRAMS(new String[]{}),
    EXCEL_DOC(new String[]{}),
    ADD_CATEGORY_LIMIT(new String[]{}),
    INCREMENT_BALANCE(BALANCE.subMenuItems),
    DECREMENT_BALANCE(BALANCE.subMenuItems),
    RESET_BALANCE(BALANCE.subMenuItems),
    DETAILS(new String[]{}),
    ASSETS_LIABILITIES(new String[]{}),
    ASSETS_LIABILITIES_LIST(new String[]{}),
    CALCULATE_ASSETS_LIABILITIES(new String[]{}),
    DELETE_ASSETS_LIABILITIES(new String[]{}),
    ADD_ASSET(new String[]{}),
    ADD_LIABILITY(new String[]{}),
    REPORT(new String[]{}),
    CHART_REPORT(MAIN_MENU.subMenuItems);

    private final String[] subMenuItems;

    StateType(String[] subMenuItems) {
        this.subMenuItems = subMenuItems;
    }
}
