package ru.vadim.tgbot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.bot.Bot;

@Component
public class Runner implements CommandLineRunner {
    private final Bot bot;

    public Runner(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run(String... args) throws Exception {
        bot.start();
    }
}
