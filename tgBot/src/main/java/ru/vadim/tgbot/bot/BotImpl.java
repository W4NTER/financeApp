package ru.vadim.tgbot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.processor.UserMessageProcessor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class BotImpl implements Bot {
    private final TelegramBot bot = new TelegramBot(System.getenv("TELEGRAM_API_KEY"));
    private final static Logger LOGGER = LogManager.getLogger();
    private final UserMessageProcessor userMessageProcessor;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private static final int GET_UPDATES_LIMIT = 100;
    private static final int GET_UPDATES_TIMEOUT = 0;
    private static final int THREAD_SLEEP_TIME_IN_MILLIS = 10;

    public BotImpl(UserMessageProcessor userMessageProcessor) {
        this.userMessageProcessor = userMessageProcessor;
    }

    @Override
    public void start() {
        LOGGER.info("bot running");
        int lastUpdateId = 0;
        while (true) {
            try {
                GetUpdatesResponse response =
                        bot.execute(new GetUpdates()
                                .limit(GET_UPDATES_LIMIT)
                                .timeout(GET_UPDATES_TIMEOUT)
                                .offset(++lastUpdateId));
                if (response.isOk()) {
                    List<Update> updates = response.updates();
                    if (updates.isEmpty()) {
                        continue;
                    }
                    lastUpdateId = updates.getLast().updateId();

                    executorService.submit(() -> sendResponses(updates));

                }
                Thread.sleep(THREAD_SLEEP_TIME_IN_MILLIS);
            } catch (InterruptedException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }

    private void sendResponses(List<Update> updates) {
        try {
            for (Update update : updates) {
                bot.execute(userMessageProcessor.process(update));
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Bot stopped");
        executorService.shutdown();
    }
}
