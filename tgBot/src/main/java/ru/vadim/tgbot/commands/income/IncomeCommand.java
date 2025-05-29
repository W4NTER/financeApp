package ru.vadim.tgbot.commands.income;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.INCOME_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.INCOME_COMMAND_DESCRIPTION;

@Component
public class IncomeCommand implements Command<SendMessage, SendResponse> {
    @Override
    public String command() {
        return INCOME_COMMAND;
    }

    @Override
    public String description() {
        return INCOME_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.INCOME;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post());
    }
}
