package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface StateHandler {
    boolean supports(String state);

    SendMessage handle(Update update);
}
