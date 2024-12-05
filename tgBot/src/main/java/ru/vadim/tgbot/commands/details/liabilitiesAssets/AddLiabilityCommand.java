package ru.vadim.tgbot.commands.details.liabilitiesAssets;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.apache.kafka.common.metrics.Stat;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.Constants.ASSETS_LIABILITIES_MENU;

@Component
public class AddLiabilityCommand implements Command {
    @Override
    public String command() {
        return "Добавить пассив";
    }

    @Override
    public String description() {
        return "Введите название и сумму операции в формате:\n<название>-<сумма>";
    }

    @Override
    public StateType state() {
        return StateType.ADD_LIABILITY;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(ASSETS_LIABILITIES_MENU).resizeKeyboard(true);
    }
}
