package ru.vadim.tgbot.commands.details;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.REPORT_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.REPORT_COMMAND_DESCRIPTION;
import static ru.vadim.tgbot.utils.constants.Constants.REPORT_MENU;

@Component
public class ReportCommand implements Command<SendMessage, SendResponse> {
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

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post());
    }
}
