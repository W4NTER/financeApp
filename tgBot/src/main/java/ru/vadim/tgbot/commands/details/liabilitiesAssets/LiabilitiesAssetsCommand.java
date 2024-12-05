package ru.vadim.tgbot.commands.details.liabilitiesAssets;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.Constants.ASSETS_LIABILITIES_MENU;

@Component
public class LiabilitiesAssetsCommand implements Command {
    @Override
    public String command() {
        return "Активы/Пассивы";
    }

    @Override
    public String description() {
        return "Выберите действие";
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(ASSETS_LIABILITIES_MENU).resizeKeyboard(true);
    }

    @Override
    public StateType state() {
        return StateType.ASSETS_LIABILITIES;
    }
}
