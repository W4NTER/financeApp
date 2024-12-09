package ru.vadim.tgbot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.commands.handlers.stateHandlers.StateHandler;
import ru.vadim.tgbot.entity.Chat;
import ru.vadim.tgbot.service.ChatService;

import java.util.List;
import java.util.Optional;

import static ru.vadim.tgbot.constants.BotAnswersConstants.UNKNOWN_COMMAND_BOT;
import static ru.vadim.tgbot.constants.Constants.LOGGER;

@Component
@AllArgsConstructor
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final List<Command> commands;
    private final ChatService chatService;
    private final List<StateHandler> handlers;

    @Override
    public SendMessage process(Update update) {
        var command = getCommand(update);
        Long chatId = update.message().chat().id();
        Chat chat = chatService.findOrCreateChat(chatId);
        if (command.isEmpty()) {
            String state = chat.getState();
            LOGGER.info(state);
            return commandToServer(state, update);
        }
        LOGGER.info(command.get());
        chatService.setState(
                update.message().chat().id(),
                command.get().state());
        LOGGER.info("process the command");
        return command.get().handle(update);
    }

    private SendMessage commandToServer(String state, Update update) {
        Long chatId = update.message().chat().id();
        Chat chat = chatService.findOrCreateChat(chatId);
        return processState(state, update);
    }

    private SendMessage processState(String state, Update update) {
        return handlers.stream()
                .filter(handler -> handler.supports(state))
                .findFirst()
                .map(handler -> handler.handle(update))
                .orElse(new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND_BOT));
    }

    private Optional<? extends Command> getCommand(Update update) {
        return commands.stream().filter(cmd ->
                cmd.command().equals(update.message().text())).findFirst();
    }
}
