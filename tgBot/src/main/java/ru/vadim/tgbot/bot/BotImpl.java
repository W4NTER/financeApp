package ru.vadim.tgbot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.config.ApplicationConfiguration;
import ru.vadim.tgbot.processor.UserMessageProcessor;

import java.util.List;

@Component
public class BotImpl implements Bot {
    private final TelegramBot bot = new TelegramBot(System.getenv("TELEGRAM_API_KEY"));
    private final static Logger LOGGER = LogManager.getLogger();
    private final UserMessageProcessor userMessageProcessor;


    public BotImpl(UserMessageProcessor userMessageProcessor) {
        this.userMessageProcessor = userMessageProcessor;
    }

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            bot.execute(userMessageProcessor.process(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void start() {
        bot.setUpdatesListener(this, exception -> {
            if (exception.response() != null) {
                exception.response().errorCode();
                exception.response().description();
            } else {
                LOGGER.info(exception.getMessage());
            }
        });
    }

    @Override
    public void stop() {

    }

    @Override
    public void close() throws Exception {

    }
}
