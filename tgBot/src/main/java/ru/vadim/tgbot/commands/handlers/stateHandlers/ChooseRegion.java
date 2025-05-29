package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.utils.state.StateType;

@Component
public class ChooseRegion implements StateHandler {
    private final FinanceAppWebClient financeAppWebClient;

    public ChooseRegion(FinanceAppWebClient financeAppWebClient) {
        this.financeAppWebClient = financeAppWebClient;
    }

    @Override
    public boolean supports(String state) {
        return StateType.CHOOSE_REGION.toString().equals(state);
    }

    @Override
    public BaseRequest<?, ?> handle(Update update) {
        return null;
    }
}
