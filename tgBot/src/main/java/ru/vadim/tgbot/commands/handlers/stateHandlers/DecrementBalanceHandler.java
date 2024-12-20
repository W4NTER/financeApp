package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.BalanceWebClient;
import ru.vadim.tgbot.dto.request.BalanceRequest;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.BotAnswersConstants.EVERYTHING_WRITE_BOT;
import static ru.vadim.tgbot.constants.BotAnswersConstants.SOMETHING_WRONG_ANSWER_FROM_BOT;

@Component
@AllArgsConstructor
public class DecrementBalanceHandler implements StateHandler {
    private final BalanceWebClient balanceWebClient;

    @Override
    public boolean supports(String state) {
        return StateType.DECREMENT_BALANCE.toString().equals(state);
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        try {
            balanceWebClient.decrementBalance(
                    new BalanceRequest(chatId, Integer.parseInt(update.message().text())));
            return new SendMessage(chatId, EVERYTHING_WRITE_BOT);
        } catch (Exception e) {
            return new SendMessage(chatId, SOMETHING_WRONG_ANSWER_FROM_BOT);
        }
    }
}
