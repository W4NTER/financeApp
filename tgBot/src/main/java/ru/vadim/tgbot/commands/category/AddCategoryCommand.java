package ru.vadim.tgbot.commands.category;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.CommandsConstants.ADD_CATEGORY_COMMAND;
import static ru.vadim.tgbot.utils.constants.CommandsConstants.ADD_CATEGORY_COMMAND_DESCRIPTION;

@Component
@AllArgsConstructor
public class AddCategoryCommand implements Command<SendMessage, SendResponse> {

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
        return new SendMessage(update.message().chat().id(), post()).replyMarkup(menu());
    }
}
