package ru.vadim.tgbot.commands.details;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.CHART_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.CHART_COMMAND_DESCRIPTION;

@Component
@AllArgsConstructor
public class ChartCommand implements Command<SendMessage, SendResponse> {
    private final FinanceAppWebClient financeAppWebClient;

    @Override
    public String command() {
        return CHART_COMMAND;
    }

    @Override
    public String description() {
        return CHART_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.CHART_REPORT;
    }


    @Override
    public SendMessage handle(Update update) {
        financeAppWebClient.chartGenerate(update.message().chat().id());
        return new SendMessage(update.message().chat().id(), post());
    }
}
