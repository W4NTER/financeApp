package ru.vadim.tgbot.commands.details;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.Constants.REPORT_MENU;

@Component
public class ReportCommand implements Command {
    @Override
    public String command() {
        return "Отчеты";
    }

    @Override
    public String description() {
        return "Выберите формат отчета";
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(REPORT_MENU).resizeKeyboard(true);
    }

    @Override
    public StateType state() {
        return StateType.REPORT;
    }
}
