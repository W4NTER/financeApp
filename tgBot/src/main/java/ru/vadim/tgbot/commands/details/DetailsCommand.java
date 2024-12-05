package ru.vadim.tgbot.commands.details;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.commands.Command;
import ru.vadim.tgbot.state.StateType;

import static ru.vadim.tgbot.Constants.DETAILS_MENU;

@Component
public class DetailsCommand implements Command {
    @Override
    public String command() {
        return "Детализация";
    }

    @Override
    public String description() {
        return "Детализация финансов";
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        return new ReplyKeyboardMarkup(DETAILS_MENU).resizeKeyboard(true);
    }

    @Override
    public StateType state() {
        return StateType.DETAILS;
    }
}
