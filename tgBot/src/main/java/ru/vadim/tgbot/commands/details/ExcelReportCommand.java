package ru.vadim.tgbot.commands.details;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.EXCEL_REPORT_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.EXCEL_REPORT_COMMAND_DESCRIPTION;

@Component
@AllArgsConstructor
public class ExcelReportCommand implements Command {
    private final FinanceAppWebClient financeAppWebClient;

    @Override
    public String command() {
        return EXCEL_REPORT_COMMAND;
    }

    @Override
    public String description() {
        return EXCEL_REPORT_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.EXCEL_DOC;
    }

    @Override
    public SendMessage handle(Update update) {
        financeAppWebClient.excelReport(update.message().chat().id());
        return Command.super.handle(update);
    }
}
