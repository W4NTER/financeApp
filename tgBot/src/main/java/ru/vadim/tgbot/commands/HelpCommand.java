package ru.vadim.tgbot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.utils.state.StateType;

import java.util.List;

@Component
public class HelpCommand implements Command {
    private final List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "вывести окно с командами";
    }

    @Override
    public StateType state() {
        return null;
    }

    @Override
    public BaseRequest handle(Update update) {
        return new SendMessage(update.message().chat().id(), post()).replyMarkup(menu());
    }

    @Override
    public String post() {
        StringBuilder builder = new StringBuilder("Список команд, которые знает этот бот:");
        for (Command value : commands) {
            if (value.command().equals("/start")) {
                continue;
            }
            builder.append(String.format("\n%s - %s", value.command(), value.description()));
        }
        return builder.toString();
    }
}
