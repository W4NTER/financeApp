package ru.vadim.tgbot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Привет, я бот который поможет тебе следить за расходами и будет держать тебя в тонусе!!! "
                + "Для прсмотра команд ты можешь ввести \"/help\"";
    }

    @Override
    public String post() {
        return description();
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post());
    }
}
