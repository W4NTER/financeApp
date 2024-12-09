package ru.vadim.tgbot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vadim.tgbot.bot.Bot;
import ru.vadim.tgbot.dto.request.LimitNotificationRequest;

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
}
