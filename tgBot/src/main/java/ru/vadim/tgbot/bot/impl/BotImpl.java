package ru.vadim.tgbot.bot.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.bot.Bot;
import ru.vadim.tgbot.config.ApplicationConfiguration;
import ru.vadim.tgbot.processor.UserMessageProcessor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class BotImpl implements Bot {
    private final TelegramBot bot;
    private final static Logger LOGGER = LogManager.getLogger();
    private final UserMessageProcessor userMessageProcessor;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private static final int GET_UPDATES_LIMIT = 100;
    private static final int GET_UPDATES_TIMEOUT = 0;

    public BotImpl(
            ApplicationConfiguration config,
            UserMessageProcessor userMessageProcessor
    ) {
        this.userMessageProcessor = userMessageProcessor;
        bot = new TelegramBot(config.telegramToken());
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public void start() {
        LOGGER.info("bot running");
        int lastUpdateId = 0;
        while (true) {
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
        }
    }

    private void sendResponses(List<Update> updates) {
        try {
            for (Update update : updates) {
                LOGGER.info("file id = {}", update.message().sticker().fileId());
                bot.execute(userMessageProcessor.process(update));
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

    @Override
    public void close() {
        LOGGER.info("Bot stopped");
        executorService.shutdown();
        bot.shutdown();
    }
}
