package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.cashService.StateService;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.commands.UnknownCommand;
import ru.vadim.tgbot.utils.constants.CommandsConstants;
import ru.vadim.tgbot.utils.state.StateType;

@Component
public class UnknownCommandHandler implements StateHandler {
    private final StateService stateService;

    public UnknownCommandHandler(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public boolean supports(String state) {
        return false;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        StateType stateType = StateType.valueOf(stateService.getState(chatId));
        return new UnknownCommand(stateType).handle(update);
    }
}
