package ru.vadim.tgbot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.bot.Bot;

@Component
public class Runner implements CommandLineRunner {
    //Нужно убрать, если нужно куда-то задеплоить, запуск и завершение работы бота есть в админ панели
    private final Bot bot;

    public Runner(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run(String... args) throws Exception {
        bot.start();
    }
}
