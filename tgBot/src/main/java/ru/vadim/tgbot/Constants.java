package ru.vadim.tgbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Constants {
    private Constants() {
    }

    public static final String[] MAIN_MENU_ARR = {"go to start"};
    public static final boolean RESIZE_KEYBOARD = true;
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String OUTCOME_TYPE = "OUTCOME";
    public static final String INCOME_TYPE = "INCOME";
    public static final String[][] CATEGORY_MENU = {
            {"Доходы", "Расходы"},
            {"Добавить категорию", "Добавить лимит"},
            {"Список операций"},
            MAIN_MENU_ARR};
    public static final String[][] BALANCE_MENU = {
            {"Увеличить баланс", "Уменьшить баланс"},
            {"Обнулить баланс"},
            MAIN_MENU_ARR};
}
