package ru.vadim.tgbot.commands.details;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.REPORT_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.REPORT_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.constants.Constants.REPORT_MENU;

@Component
public class ReportCommand implements Command {
    @Override
    public String command() {
        return REPORT_COMMAND;
    }

    @Override
    public String description() {
        return REPORT_COMMAND_DESCRIPTION;
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
