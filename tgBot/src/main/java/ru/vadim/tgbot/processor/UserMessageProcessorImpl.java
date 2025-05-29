package ru.vadim.tgbot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.cashService.StateService;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.commands.UnknownCommand;
import ru.vadim.tgbot.commands.handlers.stateHandlers.StateHandler;
import ru.vadim.tgbot.commands.handlers.stateHandlers.UnknownCommandHandler;
import ru.vadim.tgbot.utils.state.StateType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vadim.tgbot.utils.constants.Constants.*;

@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final Map<String, Command> commands;
    private final StateService stateService;
    private final List<StateHandler> handlers;
    private final UnknownCommandHandler unknownCommandHandler;

    public UserMessageProcessorImpl(
            Set<Command> commands,
            StateService stateService,
            List<StateHandler> handlers, UnknownCommandHandler unknownCommandHandler) {
        this.commands = commands.stream()
                .collect(Collectors.toMap(Command::command, Function.identity()));
        this.stateService = stateService;
        this.handlers = handlers;
        this.unknownCommandHandler = unknownCommandHandler;
    }

    @Override
    public BaseRequest<?,?> process(Update update) {
        var command = getCommand(update);
        Long chatId = update.message().chat().id();

        if (command.isEmpty()) {
            String state = stateService.getState(chatId);
            return commandToServer(state, update);
        }
        LOGGER.info("chat: {}, process command: {}", chatId,  command.get());
        stateService.setState(
                update.message().chat().id(),
                command.get().state());

        try {
            return command.get().handle(update);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            LOGGER.info(e.getStackTrace());
            return new SendMessage(chatId, "Что-то сломалось").replyMarkup(
                    new ReplyKeyboardMarkup(StateType.MAIN_MENU.getSubMenuItems(), MAIN_MENU_ARR)
                            .resizeKeyboard(RESIZE_KEYBOARD)
            );
        }
    }

    private SendMessage commandToServer(String state, Update update) {
        return processState(state, update);
    }

    private SendMessage processState(String state, Update update) {
        return handlers.stream()
                .filter(handler -> handler.supports(state))
                .findFirst()
                .map(handler -> {
                    BaseRequest<?, ?> request = handler.handle(update);
                    if (request instanceof SendMessage sm) {
                        return sm;
                    }
                    throw new IllegalStateException("Handler must return SendMessage");
                })
                .orElseGet(() -> {
                    BaseRequest<?, ?> request = unknownCommandHandler.handle(update);
                    if (request instanceof SendMessage sm) {
                        return sm;
                    }
                    throw new IllegalStateException("Not a legal value from unknown command");
                });
    }

    private Optional<? extends Command> getCommand(Update update) {
        return Optional.ofNullable(commands.get(update.message().text()));
    }
}
