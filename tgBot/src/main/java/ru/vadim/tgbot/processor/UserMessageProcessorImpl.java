package ru.vadim.tgbot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;

import java.util.List;
import java.util.Optional;

@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<Command> commands;

    public UserMessageProcessorImpl(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public SendMessage process(Update update) {
        var command = getCommand(update);
        if (command.isEmpty()) {
            return new SendMessage(update.message().chat().id(), "Такой команды не существует");
        }
        return command.get().handle(update);
    }

    public Optional<? extends Command> getCommand(Update update) {
        return commands.stream().filter(cmd ->
                cmd.command().equals(update.message().text().split(" ")[0])).findFirst();
    }
}
