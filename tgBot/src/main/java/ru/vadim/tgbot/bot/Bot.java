package ru.vadim.tgbot.bot;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.stereotype.Component;

@Component
public interface Bot extends AutoCloseable {
    <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request);

    void start();

    void close();
}
