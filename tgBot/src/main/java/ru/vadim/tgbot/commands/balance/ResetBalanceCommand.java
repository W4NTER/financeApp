package ru.vadim.tgbot.commands.balance;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.BalanceWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.RESET_BALANCE_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.RESET_BALANCE_COMMAND_DESCRIPTION;

@Component
@AllArgsConstructor
public class ResetBalanceCommand implements Command<SendMessage, SendResponse> {
    private final BalanceWebClient balanceWebClient;

    @Override
    public String command() {
        return RESET_BALANCE_COMMAND;
    }

    @Override
    public String description() {
        return RESET_BALANCE_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.RESET_BALANCE;
    }

    @Override
    public SendMessage handle(Update update) {
        balanceWebClient.resetBalance(update.message().chat().id());
        return new SendMessage(update.message().chat().id(), post());
    }
}
