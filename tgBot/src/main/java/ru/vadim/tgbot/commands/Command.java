package ru.vadim.tgbot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public interface Command {
    String command();

    String description();

    default String post() {
        return description();
    }

    default SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post());
    }
}
