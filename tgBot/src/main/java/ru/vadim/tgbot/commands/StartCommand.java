package ru.vadim.tgbot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.BalanceWebClient;
import ru.vadim.tgbot.client.FinanceAppWebClient;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.Constants.*;

@Component
public class StartCommand implements Command<SendMessage, SendResponse> {
    private final FinanceAppWebClient financeAppWebClient;
    private final BalanceWebClient balanceWebClient;
    InlineKeyboardMarkup keyboard;


    public StartCommand(FinanceAppWebClient financeAppWebClient, BalanceWebClient balanceWebClient) {
        this.financeAppWebClient = financeAppWebClient;
        this.balanceWebClient = balanceWebClient;
    }

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Привет, я бот который поможет тебе следить за расходами и будет держать тебя в тонусе!!! "
                + "Для просмотра команд ты можешь ввести \"/help\"";
    }

    @Override
    public StateType state() {
        return StateType.MAIN_MENU;
    }

    @Override
    public ReplyKeyboardMarkup menu() {
        LOGGER.info("set keyboard menu");
        return new ReplyKeyboardMarkup(state().getSubMenuItems(), MAIN_MENU_ARR).resizeKeyboard(RESIZE_KEYBOARD);
    }

    @Override
    public String post() {
        return description();
    }

    @Override
    public SendMessage handle(Update update) {
        LOGGER.info(String.format("/start by %s", update.message().chat().id()));
//        financeAppWebClient.registerChat(update.message().chat().id());
//        balanceWebClient.addBalance(update.message().chat().id());
        SendMessage message = new SendMessage(update.message().chat().id(), post()).replyMarkup(menu());
        message.replyMarkup(keyboard);
        return message;
    }

    private InlineKeyboardMarkup setInlineKeyboard() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Калининград (GMT+2)").callbackData("Europe/Kaliningrad"),
                new InlineKeyboardButton("Москва (GMT+3)").callbackData("Europe/Moscow"),
                new InlineKeyboardButton("Самара (GMT+4)").callbackData("Europe/Samara"),
                new InlineKeyboardButton("Екатеринбург (GMT+5)").callbackData("Asia/Yekaterinburg"),
                new InlineKeyboardButton("Омск (GMT+6)").callbackData("Asia/Omsk"),
                new InlineKeyboardButton("Новосибирск (GMT+7)").callbackData("Asia/Novosibirsk"),
                new InlineKeyboardButton("Иркутск (GMT+8)").callbackData("Asia/Irkutsk"),
                new InlineKeyboardButton("Якутск (GMT+9)").callbackData("Asia/Yakutsk"),
                new InlineKeyboardButton("Владивосток (GMT+10)").callbackData("Asia/Vladivostok"),
                new InlineKeyboardButton("Магадан (GMT+11)").callbackData("Asia/Magadan"),
                new InlineKeyboardButton("Камчатка (GMT+12)").callbackData("Asia/Kamchatka")
        );
    }
}
