package ru.vadim.tgbot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.entity.Chat;
import ru.vadim.tgbot.service.ChatService;

import java.util.List;
import java.util.Optional;

import static ru.vadim.tgbot.Constants.LOGGER;

@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final List<Command> commands;
    private final ChatService chatService;


    public UserMessageProcessorImpl(List<Command> commands, ChatService chatService) {
        this.commands = commands;
        this.chatService = chatService;
    }

    @Override
    public SendMessage process(Update update) {
        var command = getCommand(update);
        if (command.isEmpty()) {
            LOGGER.info("no command find");
            return new SendMessage(update.message().chat().id(), "Такой команды не существует");
        }
        LOGGER.info(command.get());
        Chat chat = chatService.findOrCreateChat(update.message().chat().id());
        chatService.setState(chat, command.get().state());
        LOGGER.info("process the command");
        return command.get().handle(update);
    }

    private Optional<? extends Command> getCommand(Update update) {
        return commands.stream().filter(cmd ->
                cmd.command().equals(update.message().text())).findFirst();
    }
}
