package ru.vadim.tgbot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface UserMessageProcessor {
    SendMessage process(Update update);
}
