package ru.vadim.tgbot.commands.category;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.constants.CommandsConstants.ADD_CATEGORY_COMMAND;
import static ru.vadim.tgbot.constants.CommandsConstants.ADD_CATEGORY_COMMAND_DESCRIPTION;

@Component
@AllArgsConstructor
public class AddCategoryCommand implements Command {

    @Override
    public String command() {
        return ADD_CATEGORY_COMMAND;
    }

    @Override
    public String description() {
        return ADD_CATEGORY_COMMAND_DESCRIPTION;
    }

    @Override
    public StateType state() {
        return StateType.ADD_CATEGORY;
    }

    @Override
    public SendMessage handle(Update update) {
        return Command.super.handle(update);
    }
}
