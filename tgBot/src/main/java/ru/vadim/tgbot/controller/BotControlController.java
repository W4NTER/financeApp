package ru.vadim.tgbot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vadim.tgbot.bot.Bot;

@RestController
@RequestMapping("/bot")
public class BotControlController {
    private final static Logger LOGGER = LogManager.getLogger();
    private final Bot bot;

    public BotControlController(Bot bot) {
        this.bot = bot;
    }

    @PostMapping("/start")
    public void startBot() {
        bot.start();
        LOGGER.info("bot started");
    }

    @PostMapping("/stop")
    public void stopBot() {
        bot.close();
        LOGGER.info("bot closed");
    }

}
