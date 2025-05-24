package ru.vadim.tgbot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.commands.handlers.stateHandlers.StateHandler;
import ru.vadim.tgbot.cashService.StateService;
import ru.vadim.tgbot.state.StateType;

import java.util.List;
import java.util.Optional;

import static ru.vadim.tgbot.constants.BotAnswersConstants.UNKNOWN_COMMAND_BOT;
import static ru.vadim.tgbot.constants.Constants.*;

@Component
@AllArgsConstructor
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final List<Command> commands;
    private final StateService stateService;
    private final List<StateHandler> handlers;

    @Override
    public SendMessage process(Update update) {
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
                .map(handler -> handler.handle(update))
                .orElse(new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND_BOT));
    }

    private Optional<? extends Command> getCommand(Update update) {
        return commands.stream().filter(cmd ->
                cmd.command().equals(update.message().text())).findFirst();
    }
}
