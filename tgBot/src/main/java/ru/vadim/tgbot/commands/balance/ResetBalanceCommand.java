package ru.vadim.tgbot.commands.balance;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

@Component
@AllArgsConstructor
public class ResetBalanceCommand implements Command {
    private final FinanceAppWebClient financeAppWebClient;

    @Override
    public String command() {
        return "Обнулить баланс";
    }

    @Override
    public String description() {
        return "Баланс успешно сброшен";
    }

    @Override
    public StateType state() {
        return StateType.RESET_BALANCE;
    }

    @Override
    public SendMessage handle(Update update) {
        financeAppWebClient.resetBalance(update.message().chat().id());
        return Command.super.handle(update);
    }
}
