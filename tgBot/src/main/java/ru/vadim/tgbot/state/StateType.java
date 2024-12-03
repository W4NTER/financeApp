package ru.vadim.tgbot.state;

public enum StateType {
    MAIN_MENU(new String[]{"Категории", "Баланс", "Детализация"}),
    CATEGORY(new String[]{"Добавить категорию", "Расходы", "Доходы", "Список операций"}),
    CATEGORY_ADD(new String[]{}),
    OUTCOME(new String[]{"Список расходов", "Добавить трату"}),
    OUTCOME_LIST(new String[]{}),
    ADD_OUTCOME(new String[]{}),
    INCOME(new String[]{"Список доходов", "Добавить доход"}),
    INCOME_LIST(new String[]{}),
    ADD_INCOME(new String[]{}),
    OPERATIONS(new String[]{}),
    BALANCE(new String[]{}),
    ASSETS_LIABILITIES(new String[]{"Графики", "Excel документ"}),
    DIAGRAMS(new String[]{}),
    EXCEL_DOC(new String[]{});

    private final String[] subMenuItems;

    StateType(String[] subMenuItems) {
        this.subMenuItems = subMenuItems;
    }

    public String[] getSubMenuItems() {
        return subMenuItems;
    }
}
