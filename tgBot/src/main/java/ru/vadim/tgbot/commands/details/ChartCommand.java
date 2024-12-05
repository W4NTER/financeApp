package ru.vadim.tgbot.commands.details;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.CHART_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.CHART_COMMAND_DESCRIPTION;

@Component
@AllArgsConstructor
public class ChartCommand implements Command {
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
        return Command.super.handle(update);
    }
}
