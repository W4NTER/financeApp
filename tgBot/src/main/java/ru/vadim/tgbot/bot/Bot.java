package ru.vadim.tgbot.bot;

import org.springframework.stereotype.Component;

@Component
public interface Bot extends AutoCloseable {
    void start() throws InterruptedException;
}
