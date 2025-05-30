package ru.vadim.tgbot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import ru.vadim.tgbot.utils.constants.CommandsConstants;
import ru.vadim.tgbot.utils.state.StateType;

public class UnknownCommand implements Command<SendMessage, SendResponse> {
    private final StateType state;

    public UnknownCommand(StateType state) {
        this.state = state;
    }

    @Override
    public String command() {
        return CommandsConstants.UNKNOWN_COMMAND;
    }

    @Override
    public String description() {
        return CommandsConstants.UNKNOWN_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return state;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        return new SendMessage(chatId, post()).replyMarkup(menu());
    }
}
