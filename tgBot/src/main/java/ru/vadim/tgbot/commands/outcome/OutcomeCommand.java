package ru.vadim.tgbot.commands.outcome;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.OUTCOME_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.OUTCOME_COMMAND_DESCRIPTION;

@Component
public class OutcomeCommand implements Command<SendMessage, SendResponse> {
    @Override
    public String command() {
        return OUTCOME_COMMAND;
    }

    @Override
    public String description() {
        return OUTCOME_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.OUTCOME;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post());
    }
}
