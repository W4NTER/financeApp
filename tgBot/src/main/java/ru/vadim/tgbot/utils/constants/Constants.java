package ru.vadim.tgbot.utils.constants;

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
    public static final String ASSET_TYPE = "ASSET";
    public static final String LIABILITY_TYPE = "LIABILITY";
    public static final String FORMAT_FOR_LOGGING_CHAT_ID = "ChatId = %d, %s";
    public static final String[][] CATEGORY_MENU = {
            {"Доходы", "Расходы"},
            {"Список операций", "Добавить лимит"},
            MAIN_MENU_ARR};
    public static final String[][] BALANCE_MENU = {
            {"Увеличить баланс", "Уменьшить баланс"},
            {"Обнулить баланс"},
            MAIN_MENU_ARR};

    public static final String[][] DETAILS_MENU = {
            {"Активы/Пассивы", "Отчеты"},
            MAIN_MENU_ARR};

    public static final String[][] REPORT_MENU = {
            {"Вывести график"},
            {"Получить Excel документ"},
            MAIN_MENU_ARR};

    public static final String[][] ASSETS_LIABILITIES_MENU = {
            {"Добавить пассив", "Добавить актив"},
            {"Список активов и пассивов"},
            {"Рассчитать", "Удалить все"},
            MAIN_MENU_ARR};
}
