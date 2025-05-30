package ru.vadim.tgbot.controller;

import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.starter_t1.aspect.annotation.LogExecution;
import ru.vadim.tgbot.bot.Bot;
import ru.vadim.tgbot.dto.request.ChartRequest;
import ru.vadim.tgbot.dto.request.ExcelReportRequest;
import ru.vadim.tgbot.dto.request.LimitNotificationRequest;

@RestController
@AllArgsConstructor
public class BotController {
    private final static Logger LOGGER = LogManager.getLogger();
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
    @LogExecution
    public ResponseEntity<Void> chartSend(@RequestBody ChartRequest request) {
        LOGGER.info(request.chart().getClass());
        bot.execute(new SendPhoto(request.chatId(), request.chart()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/excel")
    @LogExecution
    public ResponseEntity<Void> excelReport(@RequestBody ExcelReportRequest request) {
        var sendDoc = new SendDocument(request.chatId(), request.data());
        sendDoc.fileName("report.xlsx");
        bot.execute(sendDoc);
        return ResponseEntity.ok().build();
    }
}
