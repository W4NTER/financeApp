package ru.vadim.tgbot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.GO_TO_START_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.GO_TO_START_COMMAND_DESCRIPTION;

@Component
public class GoToStartCommand implements Command<SendMessage, SendResponse> {
    @Override
    public String command() {
        return GO_TO_START_COMMAND;
    }

    @Override
    public String description() {
        return GO_TO_START_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.MAIN_MENU;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post());
    }
}
