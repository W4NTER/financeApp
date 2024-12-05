package ru.vadim.tgbot.commands.category;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

@Component
@AllArgsConstructor
public class AddCategoryCommand implements Command {
    private final FinanceAppWebClient financeAppWebClient;

    @Override
    public String command() {
        return "Добавить категорию";
    }

    @Override
    public String description() {
        return "Добавление категории";
    }

    @Override
    public StateType state() {
        return StateType.ADD_CATEGORY;
    }

    @Override
    public SendMessage handle(Update update) {
//        financeAppWebClient.addCategory(update.message().chat().id(), )
        return Command.super.handle(update);
    }
}
