package ru.vadim.tgbot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vadim.tgbot.bot.Bot;
import ru.vadim.tgbot.dto.request.ChartRequest;
import ru.vadim.tgbot.dto.request.LimitNotificationRequest;
import static ru.vadim.tgbot.Constants.LOGGER;

@RestController
@AllArgsConstructor
public class BotController {
    private final Bot bot;

    @PostMapping("/limit")
    public ResponseEntity<Void> limitReached(@RequestBody LimitNotificationRequest request) {
        bot.execute(
                new SendMessage(
                        request.chatId(),
                        String.format("Лимит по категории %s исчерпан", request.categoryTitle())));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/chart")
    public ResponseEntity<Void> chartSend(@RequestBody ChartRequest request) {
        LOGGER.info(request.chart().getClass());
        bot.execute(new SendPhoto(request.chatId(), request.chart()));
        return ResponseEntity.ok().build();
    }
}
