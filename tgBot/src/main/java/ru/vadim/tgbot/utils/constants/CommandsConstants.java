package ru.vadim.tgbot.utils.constants;

import org.apache.kafka.common.protocol.types.Field;

@SuppressWarnings({"MultipleStringLiterals"})
public class CommandsConstants {
    private CommandsConstants() {}

    public static final String OPERATIONS_LIST_COMMAND = "Список операций";
    public static final String BALANCE_COMMAND = "Баланс";
    public static final String BALANCE_COMMAND_DESCRIPTION =
            "Баланс рассчитывается исходя их всех операций за месяц,"
            + " также его можно увеличить или уменьшить вручную";
    public static final String DECREMENT_BALANCE_COMMAND = "Уменьшить баланс";
    public static final String DECREMENT_BALANCE_COMMAND_DESCRIPTION =
            "Введите сумму, на которую хотите уменьшить баланс";
    public static final String INCREMENT_BALANCE_COMMAND_DESCRIPTION =
            "Введите сумму, на которую нужно уменьшить баланс";
    public static final String INCREMENT_BALANCE_COMMAND = "Увеличить баланс";
    public static final String RESET_BALANCE_COMMAND = "Обнулить баланс";
    public static final String RESET_BALANCE_COMMAND_DESCRIPTION = "Баланс успешно сброшен";
    public static final String ADD_CATEGORY_COMMAND = "Добавить категорию";
    public static final String ADD_CATEGORY_COMMAND_DESCRIPTION = "Введите название категории";
    public static final String ADD_CATEGORY_LIMIT_COMMAND_DESCRIPTION = "Введите сумму лимита";
    public static final String ADD_CATEGORY_LIMIT_COMMAND = "Добавить лимит";
    public static final String CATEGORY_LIST_COMMAND = "Категории";
    public static final String CATEGORY_LIST_COMMAND_DESCRIPTION = "Выберите категорию или добавьте новую";
    public static final String ADD_ASSET_COMMAND = "Добавить актив";
    public static final String ADD_ASSET_COMMAND_DESCRIPTION =
            "Введите название и сумму операции в формате:\n<название>-<сумма>";
    public static final String ADD_LIABILITY_COMMAND = "Добавить пассив";
    public static final String ADD_LIABILITY_COMMAND_DESCRIPTION =
            "Введите название и сумму операции в формате:\n<название>-<сумма>";
    public static final String CALCULATE_LIABILITIES_ASSETS_COMMAND = "Рассчитать";
    public static final String CALCULATE_LIABILITIES_ASSETS_COMMAND_DESCRIPTION = "Итого: ";
    public static final String DELETE_ALL_LIABILITIES_ASSETS_COMMAND = "Удалить все";
    public static final String DELETE_ALL_LIABILITIES_ASSETS_COMMAND_DESCRIPTION = "Все записи удалены";
    public static final String LIABILITIES_ASSETS_COMMAND = "Активы/Пассивы";
    public static final String LIABILITIES_ASSETS_COMMAND_DESCRIPTION = "Выберите действие";
    public static final String LIABILITIES_ASSETS_LIST_COMMAND = "Список активов и пассивов";
    public static final String LIABILITIES_ASSETS_COMMAND_LIST_DESCRIPTION = "Список пуст";
    public static final String DETAILS_COMMAND = "Детализация";
    public static final String DETAILS_COMMAND_DESCRIPTION = "Детализация финансов";
    public static final String CHART_COMMAND = "Вывести график";
    public static final String CHART_COMMAND_DESCRIPTION = "Отправлю вам графики, как только они будут готовы";
    public static final String REPORT_COMMAND = "Отчеты";
    public static final String REPORT_COMMAND_DESCRIPTION = "Выберите формат отчета";
    public static final String ADD_INCOME_COMMAND = "Добавить доход";
    public static final String ADD_INCOME_COMMAND_DESCRIPTION =
            "Введите название и сумму операции в формате:\n<название>-<сумма>";
    public static final String INCOME_COMMAND = "Доходы";
    public static final String INCOME_COMMAND_DESCRIPTION = "Выберите действие";
    public static final String INCOME_LIST_COMMAND = "Список доходов";
    public static final String INCOME_LIST_COMMAND_DESCRIPTION = "Список дрходов";
    public static final String ADD_OUTCOME_COMMAND = "Добавить трату";
    public static final String ADD_OUTCOME_COMMAND_DESCRIPTION =
            "Введите название и сумму операции в формате:\n<название>-<сумма>";
    public static final String OUTCOME_COMMAND = "Расходы";
    public static final String OUTCOME_COMMAND_DESCRIPTION = "Выберите действие";
    public static final String OUTCOME_LIST_COMMAND = "Список расходов";
    public static final String OUTCOME_LIST_COMMAND_DESCRIPTION = "Список расходов";
    public static final String GO_TO_START_COMMAND = "go to start";
    public static final String GO_TO_START_COMMAND_DESCRIPTION = "Начальное меню";
    public static final String EXCEL_REPORT_COMMAND = "Получить Excel документ";
    public static final String EXCEL_REPORT_COMMAND_DESCRIPTION =
            "Как только отчет будет готов, я пришлю его в чат";
    public static final String REGION_COMMAND_DESCRIPTION = "Регион успешно сохранен";
    public static final String UNKNOWN_COMMAND = "Несуществующая команда";
    public static final String UNKNOWN_COMMAND_DESCRIPTION = "Такой команды не существует";
}
